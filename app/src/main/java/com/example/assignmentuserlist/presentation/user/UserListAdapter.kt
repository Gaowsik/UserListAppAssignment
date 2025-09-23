package com.example.assignmentuserlist.presentation.user

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignmentuserlist.R
import com.example.assignmentuserlist.databinding.ItemUserBinding
import com.example.assignmentuserlist.domain.User

class UserListAdapter(
    var onItemClicked: (User) -> Unit
) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>(), Filterable {

    private var userList = listOf<User>()
    private var originalList = listOf<User>()


    private val searchFilter: Filter = object : Filter() {

        override fun performFiltering(input: CharSequence): FilterResults {
            val filteredList = if (input.isEmpty()) {
                originalList
            } else {

                originalList.filter {
                    it.fullName.lowercase().contains(input.toString().lowercase())
                }

            }
            return FilterResults().apply { values = filteredList }
        }

        override fun publishResults(input: CharSequence, results: FilterResults) {
            submitData(results.values as List<User>)
        }
    }

    fun submitData(filteredUserList: List<User>) {
        userList = filteredUserList
        notifyDataSetChanged()
    }


    inner class UserListViewHolder(private val mBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun onBind(user: User) {
            bindDataToUi(user = user)

            itemView.setOnClickListener {
                this@UserListAdapter.onItemClicked.invoke(user)
            }
        }


        private fun bindDataToUi(user: User) {
            mBinding.textUserName.text = user.fullName

            Glide.with(mBinding.root.context)
                .load(user.imageLargeUrl) // or imageThumbnailUrl depending on which you want
                .placeholder(R.drawable.ic_user) // optional: shown while loading
                .error(R.drawable.ic_user) // optional: shown if load fails
                .into(mBinding.imageThumbnail)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val inflate = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UserListViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) = holder.run {
        val user = userList[position]
        onBind(user = user)
    }

    override fun getFilter(): Filter {
        return searchFilter
    }

    fun setData(list: List<User>) {
        this.originalList = list
        submitData(list)
    }

}