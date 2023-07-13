package com.example.androidpractice.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.example.androidpractice.di.ViewModelFactory
import javax.inject.Inject

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding, VM: BaseViewModel>(
    val bottomNavigationId: Int,
    private val inflater: Inflate<VB>,
    val hideBottomNavigationView: Boolean = false
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    abstract val viewModel: VM

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflater(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun injectViewModelFactory()
}
