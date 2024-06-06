public class PointQuadtree {

    enum Quad {
        NW,
        NE,
        SW,
        SE
    }

    public PointQuadtreeNode root;

    public PointQuadtree() {
        this.root = null;
    }

    public static boolean insertInTree(PointQuadtreeNode root, CellTower tower){
        if(root==null){
            
            root=new PointQuadtreeNode(tower);
            for(int i = 0 ;i < 4; i ++){
                root.quadrants[i]=null;
            }
            return true;
        }
        else  {
            int L1_x= tower.x-root.celltower.x;
            int L1_y=tower.y-root.celltower.y;

            if(L1_x==0&&L1_y==0){
                return false;
            }

            if(L1_x>0&&L1_y>0){
                return insertInTree(root.quadrants[1], tower);
            }
            else if(L1_x>0&&L1_y<0){
                return insertInTree(root.quadrants[3], tower);
            }
            else if(L1_x<0&&L1_y>=0){
                return insertInTree(root.quadrants[0], tower);
            }else{
                return insertInTree(root.quadrants[2], tower);
            }

        }
    
    }
    public boolean insert(CellTower a) {
        return insertInTree(root, a);
    }

    public static boolean findTowerAt(PointQuadtreeNode root, int x , int y){

        if(root==null){
            return false;
        }

        if(root.celltower.x==x&&root.celltower.y==y){
            return true;
        }
        else  {
            int L1_x= x-root.celltower.x;
            int L1_y=y-root.celltower.y;

            

            if(L1_x>0&&L1_y>0){
                return findTowerAt(root.quadrants[1], x, y);
            }
            else if(L1_x>0&&L1_y<0){
                return findTowerAt(root.quadrants[3], x, y);
            }
            else if(L1_x<0&&L1_y>=0){
                return findTowerAt(root.quadrants[0], x, y);
            }else{
                return findTowerAt(root.quadrants[2], x, y);
            }

        }


    }
    public boolean cellTowerAt(int x, int y) {

        return findTowerAt(root, x, y);
    }
    public static void optimalTower(PointQuadtreeNode root, CellTower ans, int x, int y, int r){
        if(root==null){
            return;
        }
        double distance= Math.pow(root.celltower.x-x,2)+Math.pow(root.celltower.y-y,2);
        distance=Math.pow(distance, 0.5);
        if(distance<=r){
            if(ans==null||ans.cost>root.celltower.cost){
                ans = root.celltower;
            }
        }
        for(int i = 0 ; i < 4 ; i++){
            if(root.quadrants[i]!=null){
                optimalTower(root.quadrants[i], ans, x, y, r);
            }
        }
        return;

    }
    public CellTower chooseCellTower(int x, int y, int r) {

        CellTower ans = null;
        optimalTower(root,ans,x, y, r);
        return ans;
    }
    public static void main(String[] args) {
        PointQuadtree obj = new PointQuadtree();
        CellTower c1 = new CellTower(0,0,5);
        CellTower c2 = new CellTower(-2,0,4);
        CellTower c3 = new CellTower(2,3,10);
        CellTower c4 = new CellTower(-4,6,9);
        obj.insert(c1);
        obj.insert(c2);
        obj.insert(c3);
        obj.cellTowerAt(-2,0); // returns true
        obj.cellTowerAt(2,4); // returns false
        obj.chooseCellTower(0, 6, 5); // returns c3
        obj.insert(c4);
        obj.chooseCellTower(0, 6, 5); // returns c4
        // The current tree is shown in Figure 3.
        CellTower c5 = new CellTower(-3,7,5);
        CellTower c6 = new CellTower(-3,3,4);
        CellTower c7 = new CellTower(-6,7,2);
        CellTower c8 = new CellTower(-5,4,9);
        obj.insert(c5);
        obj.insert(c6);
        obj.insert(c7);
        obj.insert(c8);
        obj.insert(c3); // should fail
        obj.chooseCellTower(-2, 6, 2); // returns c5
        // The current tree is shown in Figure 4.


    }
}
