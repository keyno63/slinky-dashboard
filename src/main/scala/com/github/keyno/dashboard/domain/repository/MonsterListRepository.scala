package com.github.keyno.dashboard.domain.repository

import com.github.keyno.dashboard.domain.objects.{Pokemon, PokemonId, Trainer}
import io.scalajs.nodejs.fs.Fs
import io.scalajs.nodejs.fs.Fs._

import scala.concurrent.ExecutionContext
import scala.io.Source

class MonsterListRepository {

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  def getMonsterList() = ???

  def getMonsterMapList(): List[PokemonId] = {
    val filename = "scrap/src/main/resources/pokefun/s13/monster.csv"
    val pokemonIdSource = Source
      .fromFile(filename)

//    val x : String = for {
//      output <- Fs.readFileFuture(filename)
//    } yield output

    pokemonIdSource
      .getLines()
      .map {
        _.split(",")
      }
      .map {
        case Array(id, name) => PokemonId(id, name)
      }.toList
  }

  def getTrainers(): List[Trainer] = {
    // load rank match data
    val source = Source.fromFile("scrap/src/main/resources/pokefun/s13/rank.csv")
    val lines = source.getLines()
    lines.map { c =>
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
  }
}

object Sample extends scala.App {
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  val filename = "scrap/src/main/resources/pokefun/s13/monster.csv"

  for {
      output <- Fs.readFileFuture(filename)
    } {
    println(output)
  }
}
