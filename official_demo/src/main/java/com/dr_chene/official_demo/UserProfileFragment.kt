package com.dr_chene.official_demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory

/**
 * created by dr_chene on 2021/2/20
 * desc
 */
class UserProfileFragment : Fragment() {

    private val viewModel: UserProfileViewModel by viewModels(
//            factoryProducer = { SavedStateVMFactory(this) }
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner) {
            //update UI
        }
    }
}