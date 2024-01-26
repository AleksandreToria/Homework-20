package com.example.homework20.presentation.screen

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.homework20.databinding.FragmentHomeBinding
import com.example.homework20.presentation.base.BaseFragment
import com.example.homework20.presentation.event.UsersEvents
import com.example.homework20.presentation.extension.showSnackBar
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
        viewModel.onEvent(UsersEvents.CountUsers)
    }

    override fun bindViewActionListeners() {
        binding.apply {
            addBtn.setOnClickListener {
                val email = binding.email.text.toString()
                val firstName = binding.firstName.text.toString()
                val lastName = binding.lastName.text.toString()
                val ageString = binding.age.text.toString()
                val age = ageString.toIntOrNull()
                if (age != null) {
                    viewModel.onEvent(
                        UsersEvents.InsertUser(
                            Users(email, firstName, lastName, age)
                        )
                    )
                } else {
                    binding.root.showSnackBar("Please enter a valid age.")
                }
            }

            removeBtn.setOnClickListener {
                val email = binding.email.text.toString()
                val firstName = binding.firstName.text.toString()
                val lastName = binding.lastName.text.toString()
                val ageString = binding.age.text.toString()
                val age = ageString.toIntOrNull()

                if (age != null) {
                    viewModel.onEvent(
                        UsersEvents.RemoveUser(
                            Users(email, firstName, lastName, age)
                        )
                    )
                } else {
                    binding.root.showSnackBar("Please enter a valid age.")
                }
            }
            updateBtn.setOnClickListener {
                val email = binding.email.text.toString()
                val firstName = binding.firstName.text.toString()
                val lastName = binding.lastName.text.toString()
                val ageString = binding.age.text.toString()
                val age = ageString.toIntOrNull()

                if (age != null) {
                    viewModel.onEvent(
                        UsersEvents.UpdateUser(
                            Users(email, firstName, lastName, age)
                        )
                    )
                } else {
                    binding.root.showSnackBar("Please enter a valid age.")
                }
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        is HomeFragmentViewModel.UsersUiEvent.ShowStatusMessage -> {
                            val message = if (event.isSuccess) "Success" else "Error"
                            binding.status.text = message
                            val color = if (event.isSuccess) android.R.color.holo_green_dark else android.R.color.holo_red_dark
                            binding.status.setTextColor(resources.getColor(color, null))
                        }
                        is HomeFragmentViewModel.UsersUiEvent.ShowErrorMessage -> {
                            binding.root.showSnackBar(event.message)
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(state: UsersState) {
        binding.userCount.text = "User Count: ${state.countUsers}"
    }
}