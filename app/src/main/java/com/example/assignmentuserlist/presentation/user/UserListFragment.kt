package com.example.assignmentuserlist.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentuserlist.databinding.FragmentUserListBinding
import com.example.assignmentuserlist.domain.User
import com.example.assignmentuserlist.presentation.core.BaseFragment
import com.example.assignmentuserlist.presentation.extention.collectLatestLifeCycleFlow
import com.example.assignmentuserlist.presentation.utils.Constants.DEFAULT_USER_COUNT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : BaseFragment() {

    private lateinit var binding: FragmentUserListBinding
    lateinit var navController: NavController
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()


    }

    private fun init() {
        navController = findNavController()
        setTransactionRecycleView()
        setUpObservers()
        getUsers()

    }

    private fun setUpObservers() {

        this.collectLatestLifeCycleFlow(userViewModel.errorMessage) {
            showMessageDialogWithOkAction(it)
        }

        this.collectLatestLifeCycleFlow(userViewModel.userListState) {
            setDataUserListAdapter(it)
            binding.swipeRefreshLayout.isRefreshing = false

        }

        this.collectLatestLifeCycleFlow(userViewModel.isLoading) { isLoading ->
            if (isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        binding.editSearch.addTextChangedListener { input ->
            binding.recyclerViewUserList?.adapter?.let {
                (it as UserListAdapter).filter.filter(input)
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            getUsers()
        }
    }

    private fun setDataUserListAdapter(userList: List<User>) {
        binding.recyclerViewUserList.adapter?.let {
            (it as UserListAdapter).setData(userList)
        }
    }

    private fun setTransactionRecycleView() {
        binding.recyclerViewUserList.layoutManager = LinearLayoutManager(context)
        val userListRecycleAdapter = UserListAdapter { user ->
            navigateToUserDetailScreen(user.userId)
        }
        binding.recyclerViewUserList.adapter = userListRecycleAdapter
    }

    private fun navigateToUserDetailScreen(
        userId: String
    ) {
        val action =
            UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(
                userId
            )
        navController.navigate(action)
    }


    private fun getUsers() {
        userViewModel.getAllUsers(DEFAULT_USER_COUNT)
    }


}