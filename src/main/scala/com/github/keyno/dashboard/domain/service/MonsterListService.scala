package com.github.keyno.dashboard.domain.service

import com.github.keyno.dashboard.domain.objects.{Pokemon, PokemonId, Trainer}
import com.github.keyno.dashboard.domain.repository.MonsterListRepository

import scala.io.Source

class MonsterListService(repository: MonsterListRepository) {

  def getMonsterList(isRank: Boolean = true, lowerLimit: Int = 0, upperLimit: Int = 1000) = {

    val pokemonMapList: List[PokemonId] = repository.getMonsterMapList()

    val trainers = ???
    // repository.getTrainers()
  }

  def protoImpl() = {
    // load pokemon data
    val pokemonIdSource = Source
      .fromFile("scrap/src/main/resources/pokefun/s13/monster.csv")
    val pokemonMapList: List[PokemonId] = pokemonIdSource
      .getLines()
      .map {
        _.split(",")
      }
      .map {
        case Array(id, name) => PokemonId(id, name)
      }.toList
    //pokemonMapList.foreach(x => println(x))

    val isRank = false
    val lowerLimit = 0
    val upperLimit = 2100

    // load rank match data
    val source = Source.fromFile("scrap/src/main/resources/pokefun/s13/rank.csv")
    val lines = source.getLines()
    val trainers = lines.map { c =>
      c.split(",") match {
        case Array(r, p, tn, id0, item0, id1, item1, id2, item2, id3, item3, id4, item4, id5, item5)
        => Trainer(r.toInt, p.toInt, tn,
          Pokemon(id0, item0),
          Pokemon(id1, item1),
          Pokemon(id2, item2),
          Pokemon(id3, item3),
          Pokemon(id4, item4),
          Pokemon(id5, item5))
      }
    }.toList

    var data = Map.empty[String, Int]
    //trainers.foreach(x => println(x))


    //  val uniqList = toUniqList(trainers.toList)
    //  count(uniqList)

    val target = trainers
    val limit100 = if (isRank)
      sliceRankList(target, lowerLimit, upperLimit)
    else
      slicePointList(target, lowerLimit, upperLimit)
    count(pokemonMapList, limit100)
    //println(result)
  }

  def toUniqList(targetList : List[Trainer]) = {
    targetList
      .foldLeft(List[Trainer]()) {
        (l, v) => if(l.exists(e => e.name == v.name)) l else l ++ List(v)
      }
  }

  def sliceRankList(targetList : List[Trainer], lowerLimit: Int, upperLimit: Int) = {
    targetList
      .foldLeft(List.empty[Trainer])((l, v) => if (v.point >= lowerLimit && v.rank <= upperLimit) l ++ List(v) else l)
  }

  def slicePointList(targetList : List[Trainer], lowerLimit:Int, upperLimit: Int) = {
    targetList
      .foldLeft(List.empty[Trainer])((l, v) => if (v.point >= lowerLimit && v.point <= upperLimit) l ++ List(v) else l)
  }

  def count(pokemonMapList: List[PokemonId], targetList : List[Trainer]) = {
    var data = Map.empty[String, Int]
    targetList
      .map(_.getPokemons)
      .foldLeft(List.empty[Pokemon])((l, v) => l ++ v)
      .foreach(x => {
        data = update(data, x.id)
      })
    //.foreach(x => println(x))

    //println(data.toList.sortBy(_._2))
    data
      .toList
      .map(x => (convertPokemonName(pokemonMapList, x._1), x._2))
      .sortBy(_._2)
      .foldLeft(List.empty[(String, Int)])((k, v) => List(v) ++ k)
      .zipWithIndex
      .foreach(x => println(s"${x._2 + 1}: ${x._1}"))
  }

  def convertPokemonName(pokemonMapList: List[PokemonId], id: String) = {
    pokemonMapList
      .find(p => p.id == id)
      .getOrElse(PokemonId("", ""))
      .name
  }

  def update(maps: Map[String, Int], key: String) = {
    if(maps.contains(key))
      maps + (key -> maps.get(key).map(_ + 1).getOrElse(0))
    else maps + (key -> 1)
  }
}
