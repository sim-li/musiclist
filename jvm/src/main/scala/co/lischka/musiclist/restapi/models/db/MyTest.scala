package co.lischka.musiclist.restapi.models.db

import slick.jdbc.PostgresProfile.api._

// Definition of the SUPPLIERS table
class MyTest(tag: Tag) extends Table[(Int, String, String, String, String, String)](tag, "SUPPLIERS") {

  def id = column[Int]("SUP_ID", O.PrimaryKey) // This is the primary key column
  def name = column[String]("SUP_NAME")
  def street = column[String]("STREET")
  def city = column[String]("CITY")
  def state = column[String]("STATE")
  def zip = column[String]("ZIP")
  // Every table needs a * projection with the same type as the table's type parameter
  def * = (id, name, street, city, state, zip)
}
