package edu.grinnell.csc207.util;

/**
 * An implementation of two-dimensional matrices.
 *
 * @author Khanh Do - CSC-207-02
 * @author Samuel A. Rebelsky
 *
 * @param <T> The type of values stored in the matrix.
 */
public class MatrixV0<T> implements Matrix<T> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /** The width of the matrix. */
  int width;

  /** The height of the matrix. */
  int height;

  /** The default value to fill. */
  T def;

  /** The 2-D matrix we store. */
  T[][] matrix;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new matrix of the specified width and height with the given value as
   * the default.
   *
   * @param widthInput  The width of the matrix.
   * @param heightInput The height of the matrix.
   * @param defInput    The default value, used to fill all the cells.
   *
   * @throws NegativeArraySizeException If either the width or height are
   *                                    negative.
   */
  @SuppressWarnings("unchecked")
  public MatrixV0(int widthInput, int heightInput, T defInput) throws NegativeArraySizeException {
    if (widthInput <= 0 || heightInput <= 0) {
      throw new NegativeArraySizeException("Invalid width or height");
    } // if

    // Initialize fields
    this.width = widthInput;
    this.height = heightInput;
    this.def = defInput;
    this.matrix = (T[][]) new Object[heightInput][widthInput];

    // A nested for loop to fill the matrix with the default value.
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        this.matrix[row][col] = this.def;
      } // for
    } // for
  } // MatrixV0(int, int, T)

  /**
   * Create a new matrix of the specified width and height with null as the
   * default value.
   *
   * @param widthInput  The width of the matrix.
   * @param heightInput The height of the matrix.
   *
   * @throws NegativeArraySizeException If either the width or height are
   *                                    negative.
   */
  public MatrixV0(int widthInput, int heightInput) throws NegativeArraySizeException {
    this(widthInput, heightInput, null);
  } // MatrixV0

  // +--------------+------------------------------------------------
  // | Core methods |
  // +--------------+

  /**
   * Get the element at the given row and column.
   *
   * @param row The row of the element.
   * @param col The column of the element.
   *
   * @return the value at the specified location.
   *
   * @throws IndexOutOfBoundsException If either the row or column is out of
   *                                   reasonable bounds.
   */
  @Override
  public T get(int row, int col) throws IndexOutOfBoundsException {
    if (row < 0 || row >= this.height || col < 0 || col >= this.width) {
      throw new IndexOutOfBoundsException("Error: invalid row or column.");
    } // if

    return this.matrix[row][col];
  } // get(int, int)

  /**
   * Set the element at the given row and column.
   *
   * @param row The row of the element.
   * @param col The column of the element.
   * @param val The value to set.
   *
   * @throws IndexOutOfBoundsException If either the row or column is out of
   *                                   reasonable bounds.
   */
  @Override
  public void set(int row, int col, T val) throws IndexOutOfBoundsException {
    if (row < 0 || row >= this.height || col < 0 || col >= this.width) {
      throw new IndexOutOfBoundsException("Error: invalid row or column.");
    } // if

    this.matrix[row][col] = val;
  } // set(int, int, T)

  /**
   * Determine the number of rows in the matrix.
   *
   * @return the number of rows.
   */
  @Override
  public int height() {
    return this.height;
  } // height()

  /**
   * Determine the number of columns in the matrix.
   *
   * @return the number of columns.
   */
  @Override
  public int width() {
    return this.width;
  } // width()

  /**
   * Insert a row filled with the default value.
   *
   * @param row The number of the row to insert.
   *
   * @throws IndexOutOfBoundsException If the row is negative or greater than the
   *                                   height.
   */
  @SuppressWarnings("unchecked")
  @Override
  public void insertRow(int row) throws IndexOutOfBoundsException {
    if (row < 0 || row > this.height()) {
      throw new IndexOutOfBoundsException("Error: invalid row.");
    } // if

    T[][] newMatrix = (T[][]) new Object[this.height() + 1][this.width()];

    System.arraycopy(this.matrix, 0, newMatrix, 0, row);

    newMatrix[row] = (T[]) new Object[this.width()];

    for (int col = 0; col < this.width(); col++) {
      newMatrix[row][col] = this.def;
    } // for

    System.arraycopy(this.matrix, row, newMatrix, row + 1, this.height() - row);

    this.matrix = newMatrix;
    this.height++;
  } // insertRow(int)

  /**
   * Insert a row filled with the specified values.
   *
   * @param row  The number of the row to insert.
   * @param vals The values to insert.
   *
   * @throws IndexOutOfBoundsException If the row is negative or greater than the
   *                                   height.
   * @throws ArraySizeException        If the size of vals is not the same as the
   *                                   width of the matrix.
   */
  @Override
  public void insertRow(int row, T[] vals) throws IndexOutOfBoundsException, ArraySizeException {
    if (row < 0 || row > this.height()) {
      throw new IndexOutOfBoundsException("Error: invalid row.");
    } // if

    if (vals.length != this.width()) {
      throw new ArraySizeException("Error: invalid size of vals.");
    } // if

    this.insertRow(row);
    System.arraycopy(vals, 0, this.matrix[row], 0, this.width());
  } // insertRow(int, T[])

  /**
   * Insert a column filled with the default value.
   *
   * @param col The number of the column to insert.
   *
   * @throws IndexOutOfBoundsException If the column is negative or greater than
   *                                   the width.
   */
  @SuppressWarnings("unchecked")
  @Override
  public void insertCol(int col) throws IndexOutOfBoundsException {
    if (col < 0 || col > this.width()) {
      throw new IndexOutOfBoundsException("Error: invalid column.");
    } // if

    for (int row = 0; row < this.height(); row++) {
      T[] temp = (T[]) new Object[this.width() + 1];

      System.arraycopy(this.matrix[row], 0, temp, 0, col);

      temp[col] = this.def;

      System.arraycopy(this.matrix[row], col, temp, col + 1, this.width - col);

      this.matrix[row] = temp;
    } // for
    this.width++;
  } // insertCol(int)

  /**
   * Insert a column filled with the specified values.
   *
   * @param col  The number of the column to insert.
   * @param vals The values to insert.
   *
   * @throws IndexOutOfBoundsException If the column is negative or greater than
   *                                   the width.
   * @throws ArraySizeException        If the size of vals is not the same as the
   *                                   height of the matrix.
   */
  @Override
  public void insertCol(int col, T[] vals) throws IndexOutOfBoundsException, ArraySizeException {
    if (col < 0 || col > this.width()) {
      throw new IndexOutOfBoundsException("Error: invalid column.");
    } // if

    if (vals.length != this.height()) {
      throw new ArraySizeException("Error: invalid size of vals.");
    } // if

    this.insertCol(col);

    for (int row = 0; row < this.height(); row++) {
      this.matrix[row][col] = vals[row];
    } // for
  } // insertCol(int, T[])

  /**
   * Delete a row.
   *
   * @param row The number of the row to delete.
   *
   * @throws IndexOutOfBoundsException If the row is negative or greater than or
   *                                   equal to the
   *                                   height.
   */
  @Override
  public void deleteRow(int row) throws IndexOutOfBoundsException {
    if (row < 0 || row >= this.height()) {
      throw new IndexOutOfBoundsException("Error: invalid row.");
    } // if

    for (int r = 0; r < this.height() - 1; r++) {
      for (int col = 0; col < this.width(); col++) {
        if (r >= row) {
          this.matrix[r][col] = this.get(r + 1, col);
        } // if
      } // for
    } // for

    this.height--;
  } // deleteRow(int)

  /**
   * Delete a column.
   *
   * @param col The number of the column to delete.
   *
   * @throws IndexOutOfBoundsException If the column is negative or greater than
   *                                   or equal to the
   *                                   width.
   */
  @Override
  public void deleteCol(int col) throws IndexOutOfBoundsException {
    if (col < 0 || col >= this.width()) {
      throw new IndexOutOfBoundsException("Error: invalid column.");
    } // if

    for (int row = 0; row < this.height(); row++) {
      for (int c = 0; c < this.width() - 1; c++) {
        if (c >= col) {
          this.matrix[row][c] = this.get(row, c + 1);
        } // if
      } // for
    } // for

    this.width--;
  } // deleteCol(int)

  /**
   * Fill a rectangular region of the matrix.
   *
   * @param startRow The top edge / row to start with (inclusive).
   * @param startCol The left edge / column to start with (inclusive).
   * @param endRow   The bottom edge / row to stop with (exclusive).
   * @param endCol   The right edge / column to stop with (exclusive).
   * @param val      The value to store.
   *
   * @throws IndexOutOfBoundsException If the rows or columns are inappropriate.
   */
  @Override
  public void fillRegion(int startRow, int startCol, int endRow, int endCol, T val)
      throws IndexOutOfBoundsException {
    if (startRow < 0 || startCol < 0 || endRow > this.height() || endCol > this.width()) {
      throw new IndexOutOfBoundsException("Error: Rows or cols are inappropriate: " + startRow
          + ", " + startCol + ", " + endRow + ", " + endCol);
    } // if

    for (int row = startRow; row < endRow; row++) {
      for (int col = startCol; col < endCol; col++) {
        this.matrix[row][col] = val;
      } // for
    } // for
  } // fillRegion(int, int, int, int, T)

  /**
   * Fill a line (horizontal, vertical, diagonal). Fill the elements at (startRow,
   * startCol),
   * (startRow + deltaRow, startCol + deltaCol), (startRow + 2 * deltaRow,
   * startCol + 2 * deltaCol),
   * … with val. Stop when the current row or column equals or exceeds endRow or
   * endCol,
   * repectively.
   *
   * @param startRow The row to start with (inclusive).
   * @param startCol The column to start with (inclusive).
   * @param deltaRow How much to change the row in each step.
   * @param deltaCol How much to change the column in each step.
   * @param endRow   The row to stop with (exclusive).
   * @param endCol   The column to stop with (exclusive).
   * @param val      The value to store.
   *
   * @throws IndexOutOfBoundsException If the rows or columns are inappropriate.
   */
  @Override
  public void fillLine(int startRow, int startCol, int deltaRow, int deltaCol, int endRow,
      int endCol, T val) {
    if (startRow < 0 || startCol < 0 || endRow > this.height() || endCol > this.width()) {
      throw new IndexOutOfBoundsException("Error: Rows or columns are inappropriate.");
    } // if

    int row = startRow;
    int col = startCol;

    while (row < endRow && col < endCol) {
      this.matrix[row][col] = val;
      row += deltaRow;
      col += deltaCol;
    } // while
  } // fillLine(int, int, int, int, int, int, T)

  /**
   * A make a copy of the matrix. May share references (e.g., if individual
   * elements are mutable,
   * mutating them in one matrix may affect the other matrix) or may not.
   *
   * @return a copy of the matrix.
   */
  @Override
  public Matrix<T> clone() {
    Matrix<T> copy = new MatrixV0<>(this.width(), this.height(), this.def);

    for (int row = 0; row < this.height(); row++) {
      for (int col = 0; col < this.width(); col++) {
        copy.set(row, col, this.get(row, col));
      } // for
    } // for

    return copy;
  } // clone()

  /**
   * Determine if this object is equal to another object.
   *
   * @param other The object to compare.
   *
   * @return true if the other object is a matrix with the same width, height, and
   *         equal elements;
   *         false otherwise.
   */
  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object other) {
    if (other instanceof Matrix) {
      Matrix<T> otherMatrix = (Matrix<T>) other;
      if (this.width() == otherMatrix.width() && this.height() == otherMatrix.height()) {
        // a nested look to compare each element of the matrix
        for (int row = 0; row < this.height(); row++) {
          for (int col = 0; col < this.width(); col++) {
            try {
              if (!this.get(row, col).equals(otherMatrix.get(row, col))) {
                return false;
              } // if
            } catch (IndexOutOfBoundsException e) {
              if (this.matrix[row][col] == null) {
                if (otherMatrix.get(row, col) != null) {
                  return false;
                } // if
              } // if
            } // try/catch
          } // for col
        } // for row
        return true;
      } // if
      return false;
    } // if
    return false;
  } // equals(Object)

  /**
   * Compute a hash code for this matrix. Included because any object that
   * implements `equals` is
   * expected to implement `hashCode` and ensure that the hash codes for two equal
   * objects are the
   * same.
   *
   * @return the hash code.
   */
  @Override
  public int hashCode() {
    int multiplier = 7;
    int code = this.width() + multiplier * this.height();
    for (int row = 0; row < this.height(); row++) {
      for (int col = 0; col < this.width(); col++) {
        T val = this.get(row, col);
        if (val != null) {
          // It's okay if the following computation overflows, since
          // it will overflow uniformly.
          code = code * multiplier + val.hashCode();
        } // if
      } // for col
    } // for row
    return code;
  } // hashCode()
} // class MatrixV0
