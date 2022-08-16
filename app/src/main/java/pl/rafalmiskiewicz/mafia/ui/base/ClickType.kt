package pl.rafalmiskiewicz.mafia.ui.base

interface ClickType

sealed class ProductCommonClick : ClickType {
    object PlusAmount : ProductCommonClick()
    object MinusAmount : ProductCommonClick()
    object ItemClick : ProductCommonClick()
}