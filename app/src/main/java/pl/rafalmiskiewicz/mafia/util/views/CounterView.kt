package pl.rafalmiskiewicz.mafia.util.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import pl.rafalmiskiewicz.mafia.R
import pl.rafalmiskiewicz.mafia.databinding.ViewCounterBinding
import pl.rafalmiskiewicz.mafia.extensions.animateWidth
import pl.rafalmiskiewicz.mafia.extensions.hide
import pl.rafalmiskiewicz.mafia.extensions.invisible
import pl.rafalmiskiewicz.mafia.extensions.show

class CounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val plusSize = context.resources.getDimensionPixelSize(R.dimen.counter_btn_active_width)
    private var throwOutBtnSize = 0
    private var throwOutText: String? = null
    private var rightIconDrawable: Drawable? = null
    private var mode = Mode.ThrowOut
    private var isActivatedState: Boolean? = null

    private val binding = ViewCounterBinding.inflate(LayoutInflater.from(context), this)

    init {
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) = attrs?.let {
        val attributes = context.obtainStyledAttributes(it, R.styleable.CounterView)
        throwOutText = attributes.getString(R.styleable.CounterView_cvText)
            ?: "Wyrzuć"
        mode = Mode.values()[attributes.getInt(R.styleable.CounterView_cvMode, 0)]
        val rightIconId = attributes.getResourceId(
            R.styleable.CounterView_cvRightDrawable,
            //R.drawable.ic_shopping_cart
            R.drawable.ic_button_plus
        )
        rightIconDrawable = ContextCompat.getDrawable(context, rightIconId)
        setupMode(mode)
        attributes.recycle()
    }

    private fun setupMode(mode: Mode) {
        when (mode) {
            Mode.ThrowOut -> throwOutSetup()
            Mode.Cart -> cartSetup()
        }
    }

    fun setEnabledPlus(isEnabled: Boolean) {
        binding.itemProductCounterPlus.isEnabled = isEnabled
    }

    fun setCount(count: Int) {
        binding.itemProductCounter.text = count.toString()
    }

    fun setPlusListener(clickListener: () -> Unit) {

        binding.itemProductCounterPlus.setOnClickListener {
            clickListener.invoke()
        }
    }

    fun setMinusListener(clickListener: () -> Unit) {
        binding.itemProductCounterMinus.setOnClickListener {
            clickListener.invoke()
        }
    }

    private fun throwOutSetup() = with(binding) {
        itemProductCounterPlus.text = null
        itemProductCounterPlus.icon = null
        throwOutBtnSize =
            context.resources.getDimensionPixelSize(R.dimen.counter_btn_inactive_width)
        itemProductCounter.invisible()
        itemProductCounterMinus.invisible()
    }

    private fun cartSetup() = with(binding) {
        itemProductCounterPlus.text = null
        itemProductCounterPlus.width =
            context.resources.getDimensionPixelSize(R.dimen.counter_btn_active_width)
        itemProductCounterPlus.icon =
            //ResourcesCompat.getDrawable(context.resources, R.drawable.ic_shopping_cart, null)
            ResourcesCompat.getDrawable(context.resources, R.drawable.ic_button_plus, null)
        itemProductCounter.hide()
        itemProductCounterMinus.hide()
    }

    fun activateCounter(activate: Boolean) {
        if (isActivatedState == activate) return
        isActivatedState = activate
        when (mode) {
            Mode.ThrowOut -> activateThrowOut(activate)
            Mode.Cart -> activateCart(activate)
        }
    }

    private fun activateThrowOut(activate: Boolean) = with(binding) {
        if (activate) {
            itemProductCounterPlus.pivotX = throwOutBtnSize.toFloat()
            itemProductCounterPlus.text = null
            itemProductCounterPlus.animateWidth(plusSize) {
                itemProductCounterPlus.icon =
                    ResourcesCompat.getDrawable(
                        context.resources,
                        R.drawable.ic_button_plus,
                        null
                    )
                itemProductCounter.show()
                itemProductCounterMinus.show()
            }

        } else {
            itemProductCounter.text = null
            itemProductCounterPlus.icon = null
            itemProductCounter.invisible()
            itemProductCounterMinus.invisible()
            itemProductCounterPlus.animateWidth(throwOutBtnSize) {
                itemProductCounterPlus.text = throwOutText
            }
        }
    }

    private fun activateCart(activate: Boolean) = with(binding) {
        if (activate) {
            itemProductCounterPlus.icon =
                ResourcesCompat.getDrawable(context.resources, R.drawable.ic_button_plus, null)
            itemProductCounter.show()
            itemProductCounterMinus.show()

        } else {
            itemProductCounter.text = null
            itemProductCounterPlus.icon = rightIconDrawable
            itemProductCounter.hide()
            itemProductCounterMinus.hide()
        }
    }

    enum class Mode {
        ThrowOut,
        Cart
    }
}