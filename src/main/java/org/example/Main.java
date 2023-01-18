package org.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.Props;
import akka.actor.typed.ActorSystem;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the lower limit: ");
        int lowerLimit = sc.nextInt();
        System.out.println("Enter the upper limit: ");
        int upperLimit = sc.nextInt();
        System.out.println("Enter the number of intervals: ");
        int numIntervals = sc.nextInt();
        Behavior<RiemannSumActor.Command> MainRiemannSumActor = RiemannSumActor.create(BigDecimal.valueOf(lowerLimit), BigDecimal.valueOf(upperLimit), numIntervals);
        ActorSystem<RiemannSumActor.Command> system = ActorSystem.create(MainRiemannSumActor, "RiemannSumSystem");
        ActorRef<RiemannSumActor.Command> riemannSumActor = system.systemActorOf(MainRiemannSumActor, "riemannSumActor", Props.empty());
        System.out.println("The Riemann sum for the following function is: ");
        riemannSumActor.tell(new RiemannSumActor.Calculate());
    }
}
