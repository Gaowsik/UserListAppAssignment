package com.example.assignmentuserlist.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.assignmentuserlist.R
import com.example.assignmentuserlist.databinding.FragmentUserDetailBinding
import com.example.assignmentuserlist.domain.User
import com.example.assignmentuserlist.presentation.core.BaseFragment
import com.example.assignmentuserlist.presentation.extention.collectLatestLifeCycleFlow


class UserDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentUserDetailBinding
    val args: UserDetailFragmentArgs by navArgs()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    private fun init() {
        setUpObservers()
    }

    private fun setUpObservers() {
        this.collectLatestLifeCycleFlow(userViewModel.getUserById(args.userId)) {
            bindDataToUi(it)
        }
    }

    private fun bindDataToUi(user: User?) {
        user?.let {
            binding.textFullName.text = it.fullName
            binding.textEmail.text = it.email
            binding.textPhone.text = it.phone

            Glide.with(binding.imageProfile.context)
                .load(it.imageLargeUrl)
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .circleCrop()
                .into(binding.imageProfile)
        }
    }
}