/*****************************************************************************
 * File: Chainlightning.java
 * Purpose: For creation scene transactions depending on weapon specification
 * Notice: (c) 2020 Nikolay Kuzmichev. All rights reserved.
 *****************************************************************************/

package main.actions.weapons.spells;

import main.actions.ActionDescriber;

import main.actions.weapons.Weapon;
import main.actions.weapons.properties.SpellProperty;

import main.objects.Position;
import main.objects.characters.Character;

import main.transactions.ChangeHPTransaction;
import main.transactions.InfoTransaction;
import main.transactions.Transaction;

import java.util.ArrayList;
import java.util.Map;

/**
 * Weapon specification
 *
 * Name: Chainlightning
 * Target: All characters excluding attacker
 * Damage : Depends on character
 * Properties: Spell
 */

public class Chainlightning extends Weapon implements SpellProperty {
    public ActionDescriber getDescriber() {
        return SpellsList.CHAINLIGHTNING;
    }

    @Override
    public ArrayList<Transaction> attack(Map<Position, Character> battlefield, Character attacker) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        if (getBlocked()) {
            String message = String.format("Spell \"%s\" has been casted by the character already.", getDescriber().getName());
            transactions.add(new InfoTransaction(message));
            return transactions;
        }

        for (Position position : battlefield.keySet()) {
            Character target = battlefield.get(position);

            if (target == attacker) {
                continue;
            }

            transactions.add(new ChangeHPTransaction(attacker, target, this.getClass(), -attacker.getPower()));
            String message = String.format("Magician \"%s\" is attacking \"%s\" on %d hp.",
                    attacker.getName(), target.getName(), attacker.getPower());
            transactions.add(new InfoTransaction(message));
        }

        setBlocked(true);
        return transactions;
    }
}