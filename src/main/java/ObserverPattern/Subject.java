/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ObserverPattern;

/**
 *
 * @author PC
 */
public interface Subject {
    
    public abstract void setVisualState(boolean b);

    public abstract boolean getVisualState();

    public void attach(Observer observer);

    public void notifyAllObservers();
}
