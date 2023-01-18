package org.example;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class RiemannSumActor extends AbstractBehavior<RiemannSumActor.Command> {

    interface Command {}
    public static class Calculate implements Command {}

    private final BigDecimal a;
    private final BigDecimal b;
    private final int n;

    public static Behavior<Command> create(BigDecimal a, BigDecimal b, int n) {
        return Behaviors.setup(context -> new RiemannSumActor(context, a, b, n));
    }

    private RiemannSumActor(ActorContext<Command> context, BigDecimal a, BigDecimal b, int n) {
        super(context);
        this.a = a;
        this.b = b;
        this.n = n;
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder().onMessage(Calculate.class, this::onCalculate).build();
    }

    private Behavior<Command> onCalculate(Calculate message) {
        BigDecimal h = b.subtract(a).divide(BigDecimal.valueOf(n), 100, RoundingMode.HALF_UP);
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < n; i++) {
            BigDecimal x = a.add(h.multiply(BigDecimal.valueOf(i)));
            sum = sum.add(f(x));
        }
        sum = sum.multiply(h);
        System.out.println("Riemann sum is: " + sum);
        return this;
    }

    private BigDecimal f(BigDecimal x) {
        // Replace this with the function you want to integrate
        return BigDecimal.valueOf(x.sqrt(new MathContext(100)).doubleValue() + Math.sin(x.doubleValue()));
    }
}

