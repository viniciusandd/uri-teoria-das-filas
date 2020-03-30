package app

class Retorno(
        val resultado: Resultado,
        val media: Media,
        val tabela: Pair<MutableMap<Pair<Int, Int>, Float>, Int>
)