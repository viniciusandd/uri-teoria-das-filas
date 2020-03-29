@file:Suppress("UnsafeCastFromDynamic")

package app

import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.style
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*

interface AppState : RState
{
    var tempoSimulado: String
}

class App : RComponent<RProps, AppState>() {
    override fun RBuilder.render() {
        div("container") {
            div("row") {
                div("col-12") {
                    h3 {
                        attrs.style = kotlinext.js.js {
                            color = "white"
                        }
                        +"Teoria das Filas"
                    }

                    form {
                        attrs.id = "formularioEntrada"
                        attrs.style = kotlinext.js.js { color = "white" }
                        p {
                            +"Tempo a ser simulado (minutos): "
                            input {
                                attrs.onChangeFunction = {
                                    val target = it.target as HTMLInputElement
                                    setState {
                                        tempoSimulado = target.value
                                    }
                                }
                            }
                        }
                        p {
                            +"Chegadas: "
                            input { attrs.id = "entradaA1" }
                            input { attrs.id = "entradaB1" }
                            input { attrs.id = "entradaC1" }
                        }
                        p {
                            +"Serviço: "
                            input { attrs.id = "entradaA2" }
                            input { attrs.id = "entradaB2" }
                            input { attrs.id = "entradaC2" }
                        }
                    }

                    button(classes = "btn btn-primary") {
                        +"Simular"
                        attrs.onClickFunction = {
                            println(state.tempoSimulado)
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
