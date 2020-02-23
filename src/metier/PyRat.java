package metier;

import java.util.*;

public class PyRat {

    private List<Point> cheeses;
    private boolean[][] matriceFromages;
    private boolean [][] matriceAtteint;
    private  List<Point> pointAccessible;
    private Map<Point, List<Point>> labyComplet;

    /* Méthode appelée une seule fois permettant d'effectuer des traitements "lourds" afin d'augmenter la performace de la méthode turn. */
    public void preprocessing(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        Point p = (new Point (0, 6));
        cheeses = fromages;

        matriceFromages = new boolean[labyWidth][labyHeight];
        matriceAtteint = new boolean[labyWidth][labyHeight];

        for (Point pf: fromages) {
            matriceFromages[pf.getX()][pf.getY()] = true;
        }

        labyComplet = laby;
        pointAccessible = new ArrayList<>();
    }

    /* Méthode de test appelant les différentes fonctionnalités à développer.
        @param laby - Map<Point, List<Point>> contenant tout le labyrinthe, c'est-à-dire la liste des Points, et les Points en relation (passages existants)
        @param labyWidth, labyHeight - largeur et hauteur du labyrinthe
        @param position - Point contenant la position actuelle du joueur
        @param fromages - List<Point> contenant la liste de tous les Points contenant un fromage. */
    public void turn(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        Point pt1 = new Point(0,0);
        Point pt2 = new Point(1,5);
        System.out.println((fromageIci(pt1) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt1);
        System.out.println((fromageIci_EnOrdreConstant(pt2) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt2);
        System.out.println((passagePossible(pt1, pt2) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt2);
        System.out.println((passagePossible_EnOrdreConstant(pt1, pt2) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt2);
        System.out.println("Liste des points inatteignables depuis la position " + position + " : " + pointsInatteignables(position));
    }

    /* Regarde dans la liste des fromages s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci(Point pos) {
        return cheeses.contains(pos);
    }

    /* Regarde de manière performante (accès en ordre constant) s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci_EnOrdreConstant(Point pos) {
        return matriceFromages[pos.getX()][pos.getY()];
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a ».
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible(Point de, Point a) {
        List<Point> lpt = labyComplet.get(de);
        return lpt.contains(a);
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a »,
        mais sans devoir parcourir la liste des Points se trouvant dans la Map !
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible_EnOrdreConstant(Point de, Point a) {
        return false;
    }

    /* Retourne la liste des points qui ne peuvent pas être atteints depuis la position « pos ».
        @return la liste des points qui ne peuvent pas être atteints depuis la position « pos ». */
    private List<Point> pointsInatteignables(Point pos) {
        List<Point> lstAtteint= new ArrayList<>();
        List<Point> pointsAtteints = inatteignablesRec(pos, lstAtteint);
        for (Point pa: pointsAtteints) {
            matriceAtteint[pa.getX()][pa.getY()] = true;
        }
        List<Point> pasAtteint= new ArrayList<>();
        for(int i=0; i<matriceAtteint.length; i++)
        {
            for(int j=0; j<matriceAtteint[i].length; j++)
            {
                if (matriceAtteint[i][j] == false) {
                    pasAtteint.add(new Point(i,j));
                }
            }
        }
        return pasAtteint;
    }

    private List<Point> inatteignablesRec(Point pos, List<Point> lstAtteint) {
        List<Point> listsPoint = labyComplet.get(pos);
        for (Point pt : listsPoint) {
            if (!lstAtteint.contains(pt)) {
                lstAtteint.add(pt);
                inatteignablesRec(pt, lstAtteint);
            }
        }
        return lstAtteint;
    }
}