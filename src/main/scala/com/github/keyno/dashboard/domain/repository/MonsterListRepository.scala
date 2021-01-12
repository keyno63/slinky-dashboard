package com.github.keyno.dashboard.domain.repository

import com.github.keyno.dashboard.domain.objects.PokemonId

import scala.io.Source

class MonsterListRepository {

  def getMonsterList() = ???

  def getMonsterMapList(): List[PokemonId] = {
    val pokemonIdSource = Source
      .fromFile("scrap/src/main/resources/pokefun/s13/monster.csv")
    pokemonIdSource
      .getLines()
      .map {
        _.split(",")
      }
      .map {
        case Array(id, name) => PokemonId(id, name)
      }.toList
  }

}
