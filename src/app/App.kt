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

                    footerUi()
                }
            }
        }
    }

    private fun RBuilder.footerUi() {
        footer {
            a(href = "https://github.com/viniciusandd", target = "_blank") {
                i("fa fa-copyright") {
                }
                +" Vinicius Emanoel Andrade - 2020"
            }
        }
    }

    fun Float.format(digits: Int): String = this.asDynamic().toFixed(digits)

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
                    for (n in 0..it.second-1) {
                        tr {
                            td { +"$n" }
                            td { +"P($n)" }
                            for (i in 0..2) {
                                td { +"${it.first.get(Pair(n, i))?.format(2)}" }
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
            div("divComBorda") {
                form(classes = "formularioSaida") {
                    attrs.style = js { color = "white" }

                    div {
                        +"Nº Médio de Carros no Sistema: "
                        input {
                            attrs.defaultValue = it.lA.format(2)
                        }
                        input {
                            attrs.defaultValue = it.lB.format(2)
                        }
                        input {
                            attrs.defaultValue = it.lC.format(2)
                        }
                    }

                    div {
                        +"Tempo Médio Despendido no Sistema: "
                        input {
                            attrs.defaultValue = it.wA.format(2)
                        }
                        input {
                            attrs.defaultValue = it.wB.format(2)
                        }
                        input {
                            attrs.defaultValue = it.wC.format(2)
                        }
                    }

                    div {
                        +"Taxa Média de Ocupação no Servidor: "
                        input {
                            attrs.defaultValue = it.pA.format(2)
                        }
                        input {
                            attrs.defaultValue = it.pB.format(2)
                        }
                        input {
                            attrs.defaultValue = it.pC.format(2)
                        }
                    }
                }
            }
        }
    }

    private fun RBuilder.formularioSaidaUi()
    {
        state.retorno?.resultado?.let {
            div("divComBorda") {
                form(classes = "formularioSaida") {
                    attrs.style = js { color = "white" }

                    div {
                        +"Chegadas: "
                        input {
                            attrs.defaultValue = it.chegadaA.format(2)
                        }
                        input {
                            attrs.defaultValue = it.chegadaB.format(2)
                        }
                        input {
                            attrs.defaultValue = it.chegadaC.format(2)
                        }
                    }

                    div {
                        +"Serviço: "
                        input {
                            attrs.defaultValue = it.servicoA.format(2)
                        }
                        input {
                            attrs.defaultValue = it.servicoB.format(2)
                        }
                        input {
                            attrs.defaultValue = it.servicoC.format(2)
                        }
                    }
                }
            }
        }
    }

    private fun RBuilder.formularioEntradaUi()
    {
        div("divComBorda") {
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
                p {
                    attrs.style = js { margin = "15px" }
                    +"Probabilidade de Clientes(MAX): "
                    input {
                        attrs.onChangeFunction = {
                            val target = it.target as HTMLInputElement
                            setState { simulacao.maxClientes = target.value }
                        }
                    }
                }
                if (state.retorno != null)
                {
                    input(classes = "btn btn-danger btn-sm", type = InputType.reset) {
                        attrs.id = "btnRedefinir"
                        attrs.onClickFunction = {
                            setState {
                                retorno = null
                            }
                        }
                    }
                }
            }
        }
    }

    private fun RBuilder.botoesUi() {
        if (state.retorno == null)
        {
            button(classes = "btn btn-primary btn-lg") {
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

fun RBuilder.app() = child(App::class) {}
