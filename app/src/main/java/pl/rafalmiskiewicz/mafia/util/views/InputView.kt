package pl.rafalmiskiewicz.mafia.util.views

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.*
import pl.rafalmiskiewicz.mafia.R
import pl.rafalmiskiewicz.mafia.databinding.ViewInputBinding
import pl.rafalmiskiewicz.mafia.extensions.*


@BindingMethods(
    BindingMethod(
        type = InputView::class,
        attribute = "afterTextChanged",
        method = "setAfterTextChanged"
    ),
    BindingMethod(
        type = InputView::class,
        attribute = "content",
        method = "setContent"
    ),
    BindingMethod(
        type = InputView::class,
        attribute = "contentAttrChanged",
        method = "setContentChanged"
    ),
    BindingMethod(
        type = InputView::class,
        attribute = "isDisabled",
        method = "setDisabledState"
    ),
    BindingMethod(
        type = InputView::class,
        attribute = "hint",
        method = "setHint"
    )
)
@InverseBindingMethods(
    InverseBindingMethod(
        type = InputView::class,
        attribute = "content",
        method = "getContent"
    )
)
class InputView constructor(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(context, attrs) {

    private var isActive = false
    private var hasFocus = false

    private val binding = ViewInputBinding.inflate(LayoutInflater.from(context), this)

    init {
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.InputView)

        if (attributes.hasValue(R.styleable.InputView_label)) {
            binding.inputLabelTv.text = attributes.getString(
                R.styleable.InputView_label
            )
        }
        attributes.recycle()
    }

    fun setHint(hint: String?) {
        binding.inputEt.hint = hint
    }

    private fun setFocusListener() = with(binding.inputEt) {
        setOnFocusChangeListener { _, isFocused ->
            hasFocus = isFocused
            if (isFocused) {
                setActiveState()
            } else {
                setDefaultState()
            }
        }
    }

    fun setDefaultState() = with(binding) {
        inputEt.setBackground(R.drawable.bg_input_view)
        inputEt.setBackgroundTint(R.color.black)
        inputLabelTv.setColor(R.color.black)
        inputEt.isEnabled = true
    }

    private fun setActiveState() = with(binding) {
        inputEt.setBackground(R.drawable.bg_input_view)
        inputEt.setBackgroundTint(R.color.colorPrimary)
        inputLabelTv.setColor(R.color.colorPrimary)
        inputEt.isEnabled = true
    }

    fun setDisabledState(isDisabled: Boolean) = with(binding) {
        if (isDisabled) {
            inputEt.setBackground(R.drawable.bg_input_view_disabled)
            inputLabelTv.setColor(R.color.black)
            inputEt.isEnabled = false
        } else {
            setDefaultState()
        }
    }

    private fun closeKeyboard(view: View) {
        val inputMethodManager =
            binding.root.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }

    fun setAfterTextChanged(listener: TextChangedListener) {
        binding.inputEt.afterTextChanged {
            if (isActive && hasFocus) {
                setActiveState()
            } else {
                setDefaultState()
            }
            listener.onTextChangedListener(it)
        }
    }

    fun setContentChanged(listener: InverseBindingListener) {
        binding.inputEt.afterTextChanged {
            if (isActive && hasFocus) {
                setActiveState()
            } else {
                setDefaultState()
            }
            listener.onChange()
        }
    }

    fun setOnHintClickListener(string: String, listener: OnClickListener) {
        val bracketStartPosition = string.indexOf("(")
        val bracketEndPosition = string.indexOf(")")
        val myClickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                view.invalidate()
                listener.onClick(view)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = ContextCompat.getColor(context, R.color.black)
                ds.typeface = Typeface.DEFAULT_BOLD
                ds.isUnderlineText = true
            }
        }
        val ss = SpannableString(string)
        ss.setSpan(
            myClickableSpan,
            bracketStartPosition,
            bracketEndPosition.plus(1),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.inputHintTv.apply {
            show()
            text = ss
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    fun setContent(text: String?) {
        if (text != getContent()) {
            binding.inputEt.setText(text)
        }
    }

    fun getContent(): String? = binding.inputEt.text.toString()

    interface TextChangedListener {
        fun onTextChangedListener(text: String?)
    }

}