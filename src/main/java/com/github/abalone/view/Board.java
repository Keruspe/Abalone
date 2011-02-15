package com.github.abalone.view;

import com.github.abalone.config.Config;
import com.github.abalone.controller.GameController;
import com.github.abalone.elements.Ball;
import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import com.github.abalone.util.Direction;
import com.github.abalone.util.GameState;
import com.kitfox.svg.app.beans.SVGIcon;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author sardemff7
 */
class Board extends JPanel implements MouseListener
{
    private SVGIcon board;
    private Double boardScale = -1.0;
    private SVGIcon whiteBall;
    private SVGIcon blackBall;
    private Integer origX = 0;
    private Integer origY = 0;
    private HashSet<Coords> selectedBalls;
    private SVGIcon selection;
    private DirectionSelector selector;
    private final Window window;

    Board(Window window)
    {
        this.window = window;

        this.selectedBalls = new HashSet();

        this.themeUpdate();

        this.addMouseListener(this);
    }
    
    void computeBoardScale(Boolean force)
    {
        if (( ! force ) && ( this.boardScale > -1 ))
                return;
        Dimension target = new Dimension(1500, 1500);
        Dimension container = this.getSize();
        Double s = 1.0;
        if ( ( target.width <= container.width ) && ( target.height <= container.height ) )
            {} // It already fits in container
        else if ( (target.width > container.width) && (target.height <= container.height) )
            s = (double)container.width / (double)target.width; // It does not fit horizontaly
        else if ( (target.width <= container.width) && (target.height > container.height) )
            s = (double)container.height / (double)target.height; // It does not fit verticaly
        else if(target.width == target.height)
        {
            if(container.width <= container.height)
                s = (double)container.width / (double)target.width;
            else
                s = (double)container.height / (double)target.height;
        }
        
        Dimension scaled = new Dimension((int)(target.width * s), (int)(target.height * s));
        this.board.setPreferredSize(scaled);

        this.boardScale = s;
        this.origX = (container.width - scaled.width) / 2;
        this.origY = (container.height - scaled.height) / 2;

        Dimension ballSize = new Dimension((int)(100.0 * s), (int)(100.0 * s));
        this.whiteBall.setPreferredSize(ballSize);
        this.blackBall.setPreferredSize(ballSize);
        this.selection.setPreferredSize(ballSize);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        this.computeBoardScale(false);
        this.board.paintIcon(this, g, this.origX, this.origY);
        this.paintBalls(g);
    }

    private void paintBalls(Graphics g)
    {
        for (Ball b: com.github.abalone.elements.Board.getInstance().getBalls() )
        {
            Coords coords = b.getCoords();
            Point point = this.getPoint(coords);
            switch ( b.getColor() )
            {
                case WHITE:
                    this.whiteBall.paintIcon(this, g, point.x, point.y);
                break;
                case BLACK:
                    this.blackBall.paintIcon(this, g, point.x, point.y);
                break;

            }
            if ( this.selectedBalls.contains(coords) )
            {
                this.selection.paintIcon(this, g, point.x, point.y);
            }
        }
    }

    private Point getPoint(Coords coords)
    {
            Integer r = coords.getRow();
            Integer c = coords.getCol();
            Double bX = 180.0 + Math.abs(r) * 65.0 + c * 130.0;
            Double bY = (700.0 + 110.0 * r);
            Integer x = this.origX + (int)(bX * this.boardScale);
            Integer y = this.origY + (int)(bY * this.boardScale);

            return new Point(x, y);
    }

    private Coords getCoords(Point point)
    {
        point.translate(-this.origX, -this.origY);
        Double bX = (double)point.x / this.boardScale - 185.0;
        Double bY = (double)point.y / this.boardScale - 750.0;

        Integer r, c;
        if ( ( bY > -50 ) && ( bY < 50 ) )
            r = 0;
        else if ( ( bY > -160 ) && ( bY < -60 ) )
            r = -1;
        else if ( ( bY > -270 ) && ( bY < -170 ) )
            r = -2;
        else if ( ( bY > -380 ) && ( bY < -280 ) )
            r = -3;
        else if ( ( bY > -490 ) && ( bY < -390 ) )
            r = -4;
        else if ( ( bY > 60 ) && ( bY < 160 ) )
            r = 1;
        else if ( ( bY > 170 ) && ( bY < 270 ) )
            r = 2;
        else if ( ( bY > 280 ) && ( bY < 380 ) )
            r = 3;
        else if ( ( bY > 390 ) && ( bY < 490 ) )
            r = 4;
        else
            return null;

        bX -= (65.0 * Math.abs(r));
        
        if ( ( bX > 0 ) && ( bX < 100 ) )
            c = 0;
        else if ( ( bX > 130 ) && ( bX < 230 ) )
            c = 1;
        else if ( ( bX > 260 ) && ( bX < 360 ) )
            c = 2;
        else if ( ( bX > 390 ) && ( bX < 490 ) )
            c = 3;
        else if ( ( bX > 520 ) && ( bX < 620 ) )
            c = 4;
        else if ( ( Math.abs(r) < 4 ) && ( bX > 650 ) && ( bX < 750 ) )
            c = 5;
        else if ( ( Math.abs(r) < 3 ) && ( bX > 780 ) && ( bX < 880 ) )
            c = 6;
        else if ( ( Math.abs(r) < 2 ) && ( bX > 910 ) && ( bX < 1010 ) )
            c = 7;
        else if ( ( Math.abs(r) < 1 ) && ( bX > 1040 ) && ( bX < 1140 ) )
            c = 8;
        else
            return null;

        Coords coords = new Coords(r, c);
        return coords;
    }

    @Override
    public void mouseClicked(MouseEvent me)
    {
        Coords coords = this.getCoords(me.getPoint());
        if ( coords == null )
            return;
        Color color = com.github.abalone.elements.Board.getInstance().elementAt(coords);
        if ( !color.isPlayer() )
            return;
        else if(this.selectedBalls.contains(coords))
            this.selectedBalls.remove(coords);
        else if ( this.selectedBalls.size() < 3 )
            this.selectedBalls.add(coords);
        else
            return;
        this.repaint();
        this.selector.updateButtons(GameController.getInstance().validDirections(this.selectedBalls));
    }

    @Override
    public void mousePressed(MouseEvent me)
    {
    }

    @Override
    public void mouseReleased(MouseEvent me)
    {
    }

    @Override
    public void mouseEntered(MouseEvent me)
    {
    }

    @Override
    public void mouseExited(MouseEvent me)
    {
    }

    void move(Direction direction)
    {
        if ( GameController.getInstance().move(this.selectedBalls, direction).equals(GameState.RUNNING) )
        {
            this.selectedBalls.clear();
            this.selector.updateButtons(null);
        }
    }

    void themeUpdate()
    {
        this.board = new SVGIcon();
        this.board.setScaleToFit(true);
        this.board.setAntiAlias(true);
        this.whiteBall = new SVGIcon();
        this.whiteBall.setScaleToFit(true);
        this.whiteBall.setAntiAlias(true);
        this.blackBall = new SVGIcon();
        this.blackBall.setScaleToFit(true);
        this.blackBall.setAntiAlias(true);
        this.selection = new SVGIcon();
        this.selection.setScaleToFit(true);
        this.selection.setAntiAlias(true);
        try
        {
            String theme = (String) Config.get("theme");
            if ( theme == null )
                theme = "classic";
            this.board.setSvgURI(getClass().getResource("game/" + theme + "/board.svg").toURI());
            this.whiteBall.setSvgURI(getClass().getResource("game/" + theme + "/white-ball.svg").toURI());
            this.blackBall.setSvgURI(getClass().getResource("game/" + theme + "/black-ball.svg").toURI());
            this.selection.setSvgURI(getClass().getResource("game/" + theme + "/selection.svg").toURI());
        }
        catch (URISyntaxException ex)
        {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.selector = new DirectionSelector(this.window, this);
    }
}
