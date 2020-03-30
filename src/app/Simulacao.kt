package app

import kotlin.math.pow

class Simulacao
{
    var tempoDaSimulacao: Float = 0.0f
    var chegadaA: Float = 0.0f
    var chegadaB: Float = 0.0f
    var chegadaC: Float = 0.0f
    var servicoA: Float = 0.0f
    var servicoB: Float = 0.0f
    var servicoC: Float = 0.0f

    fun mostrarValores()
    {
        println(this.tempoDaSimulacao)
        println(this.chegadaA)
        println(this.chegadaB)
        println(this.chegadaC)
        println(this.servicoA)
        println(this.servicoB)
        println(this.servicoC)
    }

    fun simular() : Retorno
    {
        val resultado = this.retornarResultado()
        val media = this.retornarMedia(resultado)
        //val tabela = this.calcularTabela(4)
        return Retorno(resultado, media)
    }

    fun calcularResultado(chegadaOuServico: Float) : Float
    {
        return this.tempoDaSimulacao / chegadaOuServico
    }

    fun retornarResultado() : Resultado
    {
        return Resultado(
                this.calcularResultado(this.chegadaA),
                this.calcularResultado(this.chegadaB),
                this.calcularResultado(this.chegadaC),
                this.calcularResultado(this.servicoA),
                this.calcularResultado(this.servicoB),
                this.calcularResultado(this.servicoC)
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

    fun calcularTabela(max: Int) : MutableMap<Pair<Int, String>, Float>
    {
        val chegadas = arrayListOf(this.chegadaA, this.chegadaB, this.chegadaC)
        val servicos = arrayListOf(this.servicoA, this.servicoB, this.servicoC)
        val mapa: MutableMap<Pair<Int, String>, Float> = mutableMapOf()
        for (n in 0..max)
        {
            for (i in 0..2)
            {
                var letra = ""
                when (i)
                {
                    0 -> letra = "A"
                    1 -> letra = "B"
                    2 -> letra = "C"
                }
                mapa[Pair(n, letra)] = this.formulaDeP(n, chegadas[i], servicos[i])
            }
        }
        return mapa
    }
}