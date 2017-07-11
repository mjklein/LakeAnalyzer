import scala.collection.mutable.{HashMap => HM}

// main method in "Solution" will be run as your answer
object Solution {
  val bDebugOutput = false    // **************** DEBUG OUTPUT FLAG ****************

  //  object-scoped properties for convenience
  var size: Int = 0
  var matrix: Array[Array[Array[Int]]] = null
  var cBow = 0  // Body of Water counter
  val hmBow: HM[Int, Int] = new HM[Int, Int]


  def Debug(s: String): Unit = {
    // show debug data IF appropriate
    if(bDebugOutput) println(s)
  }

  def ShowElevationMap() {
    if(!bDebugOutput) return
    for(y <- 1 to size) {
      for(x <- 1 to size) {
        print(matrix(x - 1)(y - 1)(0) + " ")
      }
      println("")
    }
  }

  def ShowBowMap() {
    if(!bDebugOutput) return
    for(y <- 1 to size) {
      for(x <- 1 to size) {
        print(matrix(x - 1)(y - 1)(1) + " ")
      }
      println("")
    }
  }

  def CountBow() = {
    for(y <- 0 to size - 1) {
      for(x <- 0 to size - 1) {
        val bowId = matrix(x)(y)(1)
        // determine if this cell is assigned to a BOX
        if(bowId != 0) {
          // determine if this BOW is already registered...
          hmBow += ((bowId, hmBow.getOrElse(bowId, 0) + 1))
        }
      }
    }
  }

  def DensitySort() = {
    val bows = hmBow.toSeq.sortWith(_._2 > _._2)
    bows.foreach( {kvp => println(kvp._2)})
  }

  def main(args: Array[String]): Unit = {
    // manual console data injection:
    // val args = "5 0 1 2 3 0 0 0 3 2 1 1 1 2 2 2 1 1 0 1 1 1 0 0 0 0".split(" ")

    if(args.length == 0) {
      println("Invalid startup argument(s); element count should be 1 (size) + (width ^ 2)")
      return
    }
    size = args(0).toInt
    // test to make sure all the cells have been defined
    if(args.length != 1 + size * size) {
      println("Invalid cell data count; should be 1 (size) + " + size * size + " elements")
      return
    }
    matrix = Array.ofDim[(Int)](size, size, 2)   // x, y, z(0) = elevation, z(1) = lakeId
    var ptr = 0
    // scrape data into matrix (x,y) starting with UL-corner
    for(y <- 0 to size - 1) {
      for(x <- 0 to size - 1) {
        ptr += 1
        matrix(x)(y)(0) = args(ptr).toInt
        Debug("Cell (" + x + ")(" + y + ") has been mapped with elevation: " + args(ptr).toInt)
      }
    }

    ShowElevationMap

    // start analytics
    for(y <- 0 to size - 1) {
      for(x <- 0 to size - 1) {
        // test for water (0 elevation)
        if(matrix(x)(y)(0) == 0) {
          // status flag will be set IF this cell is connected to water LEFT or ABOVE
          var bConnected = false
          // test to see if connected to water (on the left)
          if((x > 0) && (matrix(x - 1)(y)(1) != 0)) {
            bConnected = true
            matrix(x)(y)(1) = matrix(x - 1)(y)(1)
            Debug("Cell (" + x + ")(" + y + ") attached to LEFT BOW: " + matrix(x)(y)(1))
          }
          // see if connected to BOW 'above'
          if ((y > 0) && (matrix(x)(y - 1)(1) != 0)) {
            // the cell immediately above is a BOW
            val idBow = matrix(x)(y)(1)    // current cell value
            val idLakeAbove = matrix(x)(y-1)(1) // lakeID of the cell ABOVE
            bConnected = true
            Debug("Cell (" + x + ")(" + y + ") has a BOW located above it: " + idLakeAbove)
            if (matrix(x)(y)(1) == 0) {
              matrix(x)(y)(1) = idLakeAbove
              Debug("Cell (" + x + ")(" + y + ") attached to ABOVE BOW: " + idLakeAbove)
            } else if (matrix(x)(y)(1) != idLakeAbove) {
              // cell is connected to BOWs located both on the LEFT and ABOVE
              for (a <- 0 to size - 1) {
                for (b <- 0 to size - 1) {
                  // test to see if this cell belongs to the lake that needs to be "flipped" (re-assigned)
                  if (matrix(a)(b)(1) == idLakeAbove) {
                    matrix(a)(b)(1) = idBow
                    Debug("Cell (" + a + ")(" + b + ") flipped from BOW " + idLakeAbove + " -> " + idBow)
                  }
                }
              }
            }
          }
          if(!bConnected) {
            // we've found a 'new' body of water (lake)...
            cBow += 1
            matrix(x)(y)(1) = cBow
            Debug("Cell (" + x + ")(" + y + ") assigned to NEW BOW " + cBow)
          }
        }
      }
    }
    ShowBowMap      // display the map show BOW cell-assignments
    CountBow        // collect the BOW area-size(s) and insert into HashMap
    DensitySort     // sort Hashmap from H->L and then display the final results
  }
}