# LakeAnalyzer

This is a Scala-based console application that analyzes bodies-of-water within a data matrix "map". 

Given:

  0 1 2 3 0 \
  0 0 3 2 1 \
  1 1 2 2 2 \
  1 1 0 1 1 \
  1 0 0 0 0

The numbers represent the topographical elevation of each cell, with "0" representing a Body of Water (BOW). Using the sample data, there are 3 separate bodies of water: 
  (1) cell in the upper-right, 
  (3) cells in the upper-left, and 
  (5) cells located at the bottom of the map.
  
Cells are considered a contiguous BOW when they border one-another above -or- below -or- left -or- right.

This application analyzes the map and determines:
  1) how many individual BOWs are contained within, and 
  2) descending-order sizes for those BOWs. 

Baseline output for the example dataset would be:
    
    5
    3
    1


# Operation

To run the application, the cell-data is fed as start-up arguments. The first argument will designate the size of the map matrix (5, in this case), with the individual cells being sequenced row-major thereafter, as such:

    scala Solution 5 0 1 2 3 0 0 0 3 2 1 1 1 2 2 2 1 1 0 1 1 1 0 0 0 0 
              size ^| row 1   |  row 2  |  row 3  |  row 4  |  row 5  |



# Code notes:

1) the Main() routine has a stub to auto-inject command-line arugments (helping for IDE debugging); to
   enable that feature, un-comment the "val args" declaration (within "main")
   
2) cells are inserted into a 3-dimensional matrix: (X)(Y)(I) \
      * X(n) = horizontal position (see notes below \
      * Y(n) = vertical position (see notes below) \
      * I(0) = that individual cell's elevation \
      * I(1) = the Body-of-Water (BOW) ID assignment for that cell (or "0" if it is a land mass)

3) the app has built-in debugging/tracing capabilities. To enable, set the "bDebugOutput" flag to 'true' (located at the 
   top of the codebase). Debug output from the same sample data yields:
   
    Cell (0)(0) has been mapped with elevation: 0      
    Cell (1)(0) has been mapped with elevation: 1 \
    Cell (2)(0) has been mapped with elevation: 2 \
    Cell (3)(0) has been mapped with elevation: 3 \
    Cell (4)(0) has been mapped with elevation: 0 \
    Cell (0)(1) has been mapped with elevation: 0 \
    Cell (1)(1) has been mapped with elevation: 0 \
    Cell (2)(1) has been mapped with elevation: 3 \
    Cell (3)(1) has been mapped with elevation: 2 \
    Cell (4)(1) has been mapped with elevation: 1 \
    Cell (0)(2) has been mapped with elevation: 1     <<<< cell elevations gathered from command-line arguments \
    Cell (1)(2) has been mapped with elevation: 1 \
    Cell (2)(2) has been mapped with elevation: 2 \
    Cell (3)(2) has been mapped with elevation: 2 \
    Cell (4)(2) has been mapped with elevation: 2 \
    Cell (0)(3) has been mapped with elevation: 1 \
    Cell (1)(3) has been mapped with elevation: 1 \
    Cell (2)(3) has been mapped with elevation: 0 \
    Cell (3)(3) has been mapped with elevation: 1 \
    Cell (4)(3) has been mapped with elevation: 1 \
    Cell (0)(4) has been mapped with elevation: 1 \
    Cell (1)(4) has been mapped with elevation: 0 \
    Cell (2)(4) has been mapped with elevation: 0 \
    Cell (3)(4) has been mapped with elevation: 0 \
    Cell (4)(4) has been mapped with elevation: 0 \
    0 1 2 3 0 \
    0 0 3 2 1 \
    1 1 2 2 2                                         <<<< production of map-matrix \
    1 1 0 1 1 \
    1 0 0 0 0 \
    Cell (0)(0) assigned to NEW BOW 1 \
    Cell (4)(0) assigned to NEW BOW 2 \
    Cell (0)(1) has a BOW located above it: 1 \
    Cell (0)(1) attached to ABOVE BOW: 1 \
    Cell (1)(1) attached to LEFT BOW: 1 \
    Cell (2)(3) assigned to NEW BOW 3 <<<< runtime analytics for each water-cell \
    Cell (1)(4) assigned to NEW BOW 4 <<<< notes:  \
    Cell (2)(4) attached to LEFT BOW: 4 <<<< 1) matrix is zero-based coordinate system \
    Cell (2)(4) has a BOW located above it: 3 <<<< 2) coordinates increase from uppor-left to lower-right \
    Cell (2)(3) flipped from BOW 3 -> 4 \
    Cell (3)(4) attached to LEFT BOW: 4 \
    Cell (4)(4) attached to LEFT BOW: 4 \
    1 0 0 0 2 \
    1 1 0 0 0 \
    0 0 0 0 0 <<<< map showing the BOW ID's assignment for each cell \
    0 0 4 0 0 <<<< Note: a zero on this map indicates a landmass-cell \
    0 4 4 4 4 \
    5 \
    3   <<<< Listing of BOW-sizes, ranked large-to-small \
    1  
   
   
   
   
   
   
   
   
   
   
   
   
   
   




   
   










