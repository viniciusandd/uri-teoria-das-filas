@file:Suppress("UnsafeCastFromDynamic")

package app

import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.style
import kotlinext.js.js
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.NodeList
import react.*
import react.dom.*
import kotlin.browser.document

interface AppState : RState
{
    var simulacao: Simulacao
    var retorno: Retorno?
}

fun limparInputs()
{
}

class App : RComponent<RProps, AppState>()
{
    override fun AppState.init()
    {
        simulacao = Simulacao()
        retorno = null
    }

    override fun RBuilder.render()
    {
        div("container") {
            div("row") {
                div("col-12") {
                    tituloUi()

                    formularioEntradaUi()

                    formularioSaidaUi()

                    formularioSaida2Ui()

                    tabelaUi()

                    botoesUi()
                }
            }
        }
    }

    private fun RBuilder.tituloUi()
    {
        h2 {
            attrs.style = js {
                color = "white"
                marginTop = "15px"
                marginBottom = "25px"
            }
            +"Teoria das Filas"
        }
    }

    private fun RBuilder.tabelaUi()
    {
        state.retorno?.tabela?.let {
            table(classes = "table table-bordered") {
                attrs.style = js { color = "white" }
                thead {
                    tr {
                        th { b { +"X" } }
                        th { b { +"P(X)" } }
                        th { b { +"A" } }
                        th { b { +"B" } }
                        th { b { +"C" } }
                    }
                }
                tbody {
                    for (n in 0..4) {
                        tr {
                            td { +"$n" }
                            td { +"P($n)" }
                            for (i in 0..2) {
                                td { +"${it.get(Pair(n, i))}" }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun RBuilder.formularioSaida2Ui()
    {
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
    }

    private fun RBuilder.formularioSaidaUi()
    {
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
    }

    private fun RBuilder.formularioEntradaUi()
    {
        form {
            attrs.id = "formularioEntrada"
            attrs.style = js { color = "white" }
            div {
                +"Tempo a ser simulado (minutos): "
                input {
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState {
                            simulacao.tempoDaSimulacao = target.value
                        }
                    }
                }
            }
            div {
                +"Chegadas: "
                input {
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState { simulacao.chegadaA = target.value }
                    }
                }
                input {
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState { simulacao.chegadaB = target.value }
                    }
                }
                input {
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState { simulacao.chegadaC = target.value }
                    }
                }
            }
            div {
                +"Serviço: "
                input {
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState { simulacao.servicoA = target.value }
                    }
                }
                input {
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState { simulacao.servicoB = target.value }
                    }
                }
                input {
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState { simulacao.servicoC = target.value }
                    }
                }
            }
            if (state.retorno != null)
            {
                input(classes = "btn btn-danger", type = InputType.reset) {
                    attrs.defaultValue = "Redefinir Entradas"
                    attrs.onClickFunction = {
                        setState {
                            retorno = null
                        }
                    }
                }
            }
        }
    }

    private fun RBuilder.botoesUi() {
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

fun RBuilder.app() = child(App::class) {}
