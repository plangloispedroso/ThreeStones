
package threestones;

/**
 *
 * @author Kevin
 */
public class PointChecker {
    private int row;
    private int column;
    private int points;
    
    public PointChecker(){}
    
    public PointChecker(int r, int c, int p) {
        this.row = r;
        this.column = c;
        this.points = p;
    }
    
    public int getRow() {
        return row;
    }
    
    public void setRow(int r) {
        this.row = r;
    }
    
    public int getColumn() {
        return column;
    }
    
    public void setColumn(int c) {
        this.column = c;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void setPoints(int p) {
        this.points = p;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PointChecker other = (PointChecker) obj;
        if (this.getRow() != other.getRow())
            return false;
        if (this.getColumn()!= other.getColumn())
            return true;
        if (this.getPoints()!= other.getPoints())
            return true;
        
        return true;
        
    }
    
    @Override
    public String toString() {
        return "Row:" + row + " Column:" + column + " Points:" + points;
    }
}
