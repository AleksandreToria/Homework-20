package com.example.homework20.presentation.screen

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.homework20.databinding.FragmentHomeBinding
import com.example.homework20.presentation.base.BaseFragment
import com.example.homework20.presentation.event.UsersEvents
import com.example.homework20.presentation.model.Users
import com.example.homework20.presentation.state.UsersState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor() :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun bind() {
    }

    override fun bindViewActionListeners() {
        binding.apply {
            addBtn.setOnClickListener {
                viewModel.onEvent(
                    UsersEvents.InsertUser(
                        Users(
                            binding.firstName.text.toString(),
                            binding.lastName.text.toString(),
                            binding.email.text.toString(),
                            binding.age.text.toString().toInt()
                        )
                    )
                )
            }

            removeBtn.setOnClickListener {
                viewModel.onEvent(
                    UsersEvents.RemoveUser(
                        Users(
                            binding.firstName.text.toString(),
                            binding.lastName.text.toString(),
                            binding.email.text.toString(),
                            binding.age.text.toString().toInt()
                        )
                    )
                )
            }
            updateBtn.setOnClickListener {
                viewModel.onEvent(
                    UsersEvents.UpdateUser(
                        Users(
                            binding.firstName.text.toString(),
                            binding.lastName.text.toString(),
                            binding.email.text.toString(),
                            binding.age.text.toString().toInt()
                        )
                    )
                )
            }
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userState.collect { state ->
                    updateUI(state)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(state: UsersState) {
        binding.userCount.text = "User Count: ${state.countUsers}"
    }
}