package com.github.keyno.dashboard.domain.objects

case class Trainer(rank: Int, point: Int, name: String,
                   pokemon0: Pokemon,
                   pokemon1: Pokemon,
                   pokemon2: Pokemon,
                   pokemon3: Pokemon,
                   pokemon4: Pokemon,
                   pokemon5: Pokemon
                  ) {
  def getPokemons = {
    Seq(pokemon0, pokemon1, pokemon2, pokemon3, pokemon4, pokemon5)
  }
}
case class Pokemon(id: String, item: String)
case class PokemonId(id: String, name: String)
