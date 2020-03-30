@file:Suppress("UnsafeCastFromDynamic")

package app

import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.style
import kotlinext.js.js
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*

interface AppState : RState
{
    var simulacao: Simulacao
    var retorno: Retorno?
}

class App : RComponent<RProps, AppState>() {

    override fun AppState.init()
    {
        simulacao = Simulacao()
        retorno = null
    }

    override fun RBuilder.render()
    {
        state.retorno?.resultado?.let {
            println(it.chegadaA)
            println(it.chegadaB)
            println(it.chegadaC)
            println(it.servicoA)
            println(it.servicoB)
            println(it.servicoC)
        }

        div("container") {
            div("row") {
                div("col-12") {
                    h3 {
                        attrs.style = js {
                            color = "white"
                        }
                        +"Teoria das Filas"
                    }

                    form {
                        attrs.id = "formularioEntrada"
                        attrs.style = js { color = "white" }
                        div {
                            +"Tempo a ser simulado (minutos): "
                            input {
                                attrs.onChangeFunction = {
                                    val target = it.target as HTMLInputElement
                                    setState {
                                        simulacao.tempoDaSimulacao = target.value.toFloat()
                                    }
                                }
                            }
                        }
                        div {
                            +"Chegadas: "
                            input {
                                attrs.onChangeFunction = {
                                    val target = it.target as HTMLInputElement
                                    setState { simulacao.chegadaA = target.value.toFloat() }
                                }
                            }
                            input {
                                attrs.onChangeFunction = {
                                    val target = it.target as HTMLInputElement
                                    setState { simulacao.chegadaB = target.value.toFloat() }
                                }
                            }
                            input {
                                attrs.onChangeFunction = {
                                    val target = it.target as HTMLInputElement
                                    setState { simulacao.chegadaC = target.value.toFloat() }
                                }
                            }
                        }
                        div {
                            +"Serviço: "
                            input {
                                attrs.onChangeFunction = {
                                    val target = it.target as HTMLInputElement
                                    setState { simulacao.servicoA = target.value.toFloat() }
                                }
                            }
                            input {
                                attrs.onChangeFunction = {
                                    val target = it.target as HTMLInputElement
                                    setState { simulacao.servicoB = target.value.toFloat() }
                                }
                            }
                            input {
                                attrs.onChangeFunction = {
                                    val target = it.target as HTMLInputElement
                                    setState { simulacao.servicoC = target.value.toFloat() }
                                }
                            }
                        }
                    }

                    state.retorno?.resultado?.let {
                        div {
                            form(classes = "formularioSaida") {
                                attrs.style = js { color = "white" }

                                div {
                                    +"Chegadas: "
                                    input {
                                        attrs.defaultValue = it.chegadaA.toString()
                                    }
                                    input {
                                        attrs.defaultValue = it.chegadaB.toString()
                                    }
                                    input {
                                        attrs.defaultValue = it.chegadaC.toString()
                                    }
                                }

                                div {
                                    +"Serviço: "
                                    input {
                                        attrs.defaultValue = it.servicoA.toString()
                                    }
                                    input {
                                        attrs.defaultValue = it.servicoB.toString()
                                    }
                                    input {
                                        attrs.defaultValue = it.servicoC.toString()
                                    }
                                }
                            }
                        }
                    }

                    state.retorno?.media?.let {
                        div {
                            form(classes = "formularioSaida") {
                                attrs.style = js { color = "white" }

                                div {
                                    +"Nº Médio de Carros no Sistema: "
                                    input {
                                        attrs.defaultValue = it.lA.toString()
                                    }
                                    input {
                                        attrs.defaultValue = it.lB.toString()
                                    }
                                    input {
                                        attrs.defaultValue = it.lC.toString()
                                    }
                                }

                                div {
                                    +"Tempo Médio Despendido no Sistema: "
                                    input {
                                        attrs.defaultValue = it.wA.toString()
                                    }
                                    input {
                                        attrs.defaultValue = it.wB.toString()
                                    }
                                    input {
                                        attrs.defaultValue = it.wC.toString()
                                    }
                                }

                                div {
                                    +"Taxa Média de Ocupação no Servidor: "
                                    input {
                                        attrs.defaultValue = it.pA.toString()
                                    }
                                    input {
                                        attrs.defaultValue = it.pB.toString()
                                    }
                                    input {
                                        attrs.defaultValue = it.pC.toString()
                                    }
                                }
                            }
                        }
                    }

                    button(classes = "btn btn-primary") {
                        +"Simular"
                        attrs.onClickFunction = {
                            setState {
                                retorno = simulacao.simular()
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
