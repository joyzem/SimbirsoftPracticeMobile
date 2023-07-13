package com.example.androidpractice.screen.news.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.text.buildSpannedString
import androidx.core.text.toSpannable
import androidx.fragment.app.viewModels
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentEventDetailsBinding
import com.example.androidpractice.di.ViewModelFactory
import com.example.androidpractice.domain.model.Event
import com.example.androidpractice.screen.news.getEventDateText
import com.example.androidpractice.ui.BaseFragment
import com.example.androidpractice.ui.getAppComponent
import com.example.androidpractice.ui.navigation.findNavController
import com.example.androidpractice.ui.spans.PhoneNumberSpan
import javax.inject.Inject

class EventDetailsFragment : BaseFragment<FragmentEventDetailsBinding>(
    R.id.newsNavItem,
    FragmentEventDetailsBinding::inflate,
    true
) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: EventDetailsViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().newsSubcomponent().create().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().onBackPressed()
        }

        observe()
    }

    private fun observe() {
        val eventId: String = arguments?.getString(EVENT_ID) ?: ""
        viewModel.events.observe(viewLifecycleOwner) { events ->
            if (events != null) {
                val event = events.find {
                    it.id == eventId
                }
                event?.let {
                    setEvent(it)
                    viewModel.readEvent(eventId)
                }
            }
        }
    }

    private fun setEvent(event: Event) {
        binding.apply {
            toolbar.title = event.title
            eventTitleTextView.text = event.title
            eventDateTextView.text = getEventDateText(binding.root, event.dates)
            sponsorTextView.text = event.sponsor
            addressTextView.text = event.address
            phonesTextView.text = getSpannablePhoneNumbers(event.phoneNumbers)
            phonesTextView.movementMethod = LinkMovementMethod()
            emailTextView.text = getSpannableMail(event.email)
            emailTextView.movementMethod = LinkMovementMethod()
            descriptionTextView.text = event.description
            siteTextView.text = getSpannableSite(event.siteUrl)
            siteTextView.movementMethod = LinkMovementMethod()
        }
    }

    private fun getSpannableSite(siteUrl: String): Spannable {
        return buildSpannedString {
            append(getString(R.string.go_to_organization_site))
            val siteSpan = object : ClickableSpan() {

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = requireContext().getColor(R.color.leaf)
                    ds.isUnderlineText = true
                }

                override fun onClick(v: View) {
                    val webpage: Uri = Uri.parse(siteUrl)
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    if (intent.resolveActivity(requireContext().packageManager) != null) {
                        startActivity(intent)
                    }
                }
            }
            setSpan(siteSpan, 0, length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        }.toSpannable()
    }

    private fun getSpannableMail(email: String): Spannable {
        return buildSpannedString {
            append(getString(R.string.have_questions))
            append(" ")
            val emailSpan = object : ClickableSpan() {

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = requireContext().getColor(R.color.leaf)
                    ds.isUnderlineText = true
                }

                override fun onClick(p0: View) {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.putExtra(Intent.EXTRA_EMAIL, email)
                    if (intent.resolveActivity(requireContext().packageManager) != null) {
                        requireContext().startActivity(intent)
                    }
                }
            }
            val cursor = length
            append(getString(R.string.write_us))
            setSpan(emailSpan, cursor, length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        }.toSpannable()

    }

    private fun getSpannablePhoneNumbers(phoneNumbers: List<String>): Spannable {
        return buildSpannedString {
            var currentIndex = 0
            phoneNumbers.dropLast(1).forEach {
                appendLine(it)
                setSpan(
                    PhoneNumberSpan(it),
                    currentIndex,
                    length,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                )
                currentIndex += it.length
            }
            append(phoneNumbers.last())
            setSpan(
                PhoneNumberSpan(phoneNumbers.last()),
                currentIndex,
                length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }.toSpannable()
    }

    companion object {
        const val EVENT_ID = "event_id"
        fun newInstance(eventId: String): EventDetailsFragment {
            return EventDetailsFragment().apply {
                arguments = bundleOf(
                    EVENT_ID to eventId
                )
            }
        }
    }
}
