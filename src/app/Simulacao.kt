package app

import kotlin.math.pow

class Simulacao
{
    var tempoDaSimulacao: String = ""
    var chegadaA: String = ""
    var chegadaB: String = ""
    var chegadaC: String = ""
    var servicoA: String = ""
    var servicoB: String = ""
    var servicoC: String = ""
    var maxClientes: String = ""

    fun validar()  : Boolean
    {
        try {
            this.tempoDaSimulacao = this.tempoDaSimulacao.replace(",", "."); this.tempoDaSimulacao.toFloat()
            this.chegadaA = this.chegadaA.replace(",", "."); this.chegadaA.toFloat()
            this.chegadaB = this.chegadaB.replace(",", "."); this.chegadaB.toFloat()
            this.chegadaC = this.chegadaC.replace(",", "."); this.chegadaC.toFloat()
            this.servicoA = this.servicoA.replace(",", "."); this.servicoA.toFloat()
            this.servicoB = this.servicoB.replace(",", "."); this.servicoB.toFloat()
            this.servicoC = this.servicoC.replace(",", "."); this.servicoC.toFloat()
            this.maxClientes.toInt()
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun simular() : Retorno?
    {
        if (!this.validar()) return null
        val resultado = this.retornarResultado()
        val media = this.retornarMedia(resultado)
        val tabela = this.calcularTabela(resultado, this.maxClientes.toInt())
        return Retorno(resultado, media, Pair(tabela, this.maxClientes.toInt()))
    }

    fun calcularResultado(chegadaOuServico: Float) : Float
    {
        return this.tempoDaSimulacao.toFloat() / chegadaOuServico
    }

    fun retornarResultado() : Resultado
    {
        return Resultado(
                this.calcularResultado(this.chegadaA.toFloat()),
                this.calcularResultado(this.chegadaB.toFloat()),
                this.calcularResultado(this.chegadaC.toFloat()),
                this.calcularResultado(this.servicoA.toFloat()),
                this.calcularResultado(this.servicoB.toFloat()),
                this.calcularResultado(this.servicoC.toFloat())
        )
    }

    fun calcularL(chegada: Float, servico: Float) : Float
    {
        return chegada / (servico - chegada)
    }

    fun calcularW(chegada: Float, servico: Float) : Float
    {
        return 1 / (servico - chegada)
    }

    fun calcularP(chegada: Float, servico: Float) : Float
    {
        return chegada / servico
    }

    fun retornarMedia(resultado: Resultado) : Media
    {
        return Media(
                this.calcularL(resultado.chegadaA, resultado.servicoA),
                this.calcularL(resultado.chegadaB, resultado.servicoB),
                this.calcularL(resultado.chegadaC, resultado.servicoC),
                this.calcularW(resultado.chegadaA, resultado.servicoA),
                this.calcularW(resultado.chegadaB, resultado.servicoB),
                this.calcularW(resultado.chegadaC, resultado.servicoC),
                this.calcularP(resultado.chegadaA, resultado.servicoA),
                this.calcularP(resultado.chegadaB, resultado.servicoB),
                this.calcularP(resultado.chegadaC, resultado.servicoC)
        )
    }

    fun formulaDeP(n: Int, chegada: Float, servico: Float) : Float
    {
        return (1 - (chegada / servico)) * (chegada / servico).pow(n)
    }

    fun calcularTabela(resultado: Resultado, max: Int) : MutableMap<Pair<Int, Int>, Float>
    {
        val chegadas = arrayListOf(resultado.chegadaA, resultado.chegadaB, resultado.chegadaC)
        val servicos = arrayListOf(resultado.servicoA, resultado.servicoB, resultado.servicoC)
        val mapa: MutableMap<Pair<Int, Int>, Float> = mutableMapOf()
        for (n in 0..max)
        {
            for (i in 0..2)
            {
                mapa[Pair(n, i)] = this.formulaDeP(n, chegadas[i], servicos[i])
            }
        }
        return mapa
    }
}