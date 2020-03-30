package app

class Retorno(
        val resultado: Resultado,
        val media: Media,
        val tabela: MutableMap<Pair<Int, Int>, Float>
)