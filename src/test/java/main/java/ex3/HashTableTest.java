package main.java.ex3;

import ex3.HashTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @Test
    void count() {
    }

    @Test
    void size() {
    }

    @Test
    void put() {
        HashTable hashTable = new HashTable();
        System.out.println("colisiones" + hashTable.getCollisionsForKey("2", 3));

        //Insertar un elemento que no colisiona dentro de una tabla vacia.

        hashTable.put("1", "eric");
        Assertions.assertEquals(1,hashTable.count());
        Assertions.assertEquals(16,hashTable.size());
        Assertions.assertEquals("\n" + " bucket[1] = [1, eric]",hashTable.toString());

        //Insertar un elemento que no colisiona dentro de una tabla no vacia.

        hashTable.put("2","eric2");
        Assertions.assertEquals(2,hashTable.count());
        Assertions.assertEquals(16,hashTable.size());
        Assertions.assertEquals("\n" + " bucket[1] = [1, eric]" + "\n" + " bucket[2] = [2, eric2]",hashTable.toString());

        //Insertar un elemeto que colisiona dentro de una tabla no vacia que se colocara en la segunda posicion dentro del mismo bucket.

        hashTable.put("13","eric3");
        Assertions.assertEquals(3,hashTable.count());
        Assertions.assertEquals(16,hashTable.size());
        Assertions.assertEquals("\n" + " bucket[1] = [1, eric]" + "\n" + " bucket[2] = [2, eric2] -> [13, eric3]",hashTable.toString());

        //Insertar un elemento que colisiona dentro de una tabla no vacia que se solocara en la tercerta posicion dento del mismo bucket.

        hashTable.put("24","eric4");
        Assertions.assertEquals(4,hashTable.count());
        Assertions.assertEquals(16,hashTable.size());
        Assertions.assertEquals("\n" + " bucket[1] = [1, eric]" + "\n" + " bucket[2] = [2, eric2] -> [13, eric3] -> [24, eric4]",hashTable.toString());

        //Insertar un elemento que ya existe sobre un elemento que no colisiona dentro de una tabla no vacia.

        hashTable.put("2","eric5");
        Assertions.assertEquals(4,hashTable.count());
        Assertions.assertEquals(16,hashTable.size());
        Assertions.assertEquals("\n" + " bucket[1] = [1, eric]" + "\n" + " bucket[2] = [2, eric5] -> [13, eric3] -> [24, eric4]",hashTable.toString());

        //Insertar un elemento que ya existe sobre un elemento que si colisiona en la segunda posicion dentro de una tabla no vacia.

        hashTable.put("13","eric6");
        Assertions.assertEquals(4,hashTable.count());
        Assertions.assertEquals(16,hashTable.size());
        Assertions.assertEquals("\n" + " bucket[1] = [1, eric]" + "\n" + " bucket[2] = [2, eric5] -> [13, eric6] -> [24, eric4]",hashTable.toString());

        //Insertar un elemento que ya existe sobre un elemento que si colisiona tercera posicion dentro de una tabla no vacia.

        hashTable.put("24","eric7");
        Assertions.assertEquals(4,hashTable.count());
        Assertions.assertEquals(16,hashTable.size());
        Assertions.assertEquals("\n" + " bucket[1] = [1, eric]" + "\n" + " bucket[2] = [2, eric5] -> [13, eric6] -> [24, eric7]",hashTable.toString());

    }

    @Test
    void get() {
        HashTable hashTable = new HashTable();
        System.out.println("colisiones " + hashTable.getCollisionsForKey("2", 4));
        System.out.println("colisiones " + hashTable.getCollisionsForKey("3", 2));

        //Obtener un elemento que no colisiona dentro de una tabla vacia.
        Assertions.assertEquals(null,hashTable.get("2"));

        //obtener un elemento que colisione dentro de una tabla, primera posicion dentro del mismo bucket
        hashTable.put("2","alva");

        //obtener un elemento que colisione dentro de una tabla, segunda posicion dentro del mismo bucket
        hashTable.put("13","alva2");
        Assertions.assertEquals("alva",hashTable.get("2"));
        Assertions.assertEquals("alva2",hashTable.get("13"));

        //obtener un elemento que colisione dentro de una tabla, tercera posicion dentro del mismo bucket
        hashTable.put("24","alva3");
        Assertions.assertEquals("alva3",hashTable.get("24"));

        //obtener un elemento que no exista porque su posicion esta vacia.
        Assertions.assertEquals(null,hashTable.get("1"));

        //obtener un elemento que no exista y que su posicion este ocupada y no colisione
        hashTable.put("3","alva4");
        Assertions.assertEquals(null,hashTable.get("14"));

        //obtener un elemento que no exista y su posicion este ocupada por 3 elementos que colisionan
        Assertions.assertEquals(null,hashTable.get("35"));

    }

    @Test
    void drop() {
        HashTable hashTable = new HashTable();

        hashTable.put("1","mole");
        hashTable.put("2","mole2");
        hashTable.put("13","mole3");
        hashTable.put("24","mole4");
        Assertions.assertEquals(4,hashTable.count());
        Assertions.assertEquals(16,hashTable.size());

        //borrar un elemento que no colisione dentro de una tabla
        hashTable.drop("1");
        Assertions.assertEquals(3,hashTable.count());
        Assertions.assertEquals(16,hashTable.size());
        Assertions.assertEquals("\n" + " bucket[2] = [2, mole2] -> [13, mole3] -> [24, mole4]",hashTable.toString());

        //borrar un elemento que si colisione dentro de una tabla, primera posicion en el mismo bucket.
        hashTable.drop("2");
        Assertions.assertEquals(2,hashTable.count());
        Assertions.assertEquals(16,hashTable.size());
        Assertions.assertEquals("\n" + " bucket[2] = [13, mole3] -> [24, mole4]",hashTable.toString());

        //borrar un elemento que si colisione dentro de una tabla, segunda posicion en el mismo bucket.
        hashTable.put("2","mole2");
        hashTable.drop("24");
        Assertions.assertEquals(2,hashTable.count());
        Assertions.assertEquals(16,hashTable.size());
        Assertions.assertEquals("\n" + " bucket[2] = [13, mole3] -> [2, mole2]",hashTable.toString());

        //borrar un elemento que si colisione dentro de una tabla, tercera posicion en el mismo bucket.
        hashTable.put("24","mole4");
        hashTable.drop("24");
        Assertions.assertEquals(2,hashTable.count());
        Assertions.assertEquals(16,hashTable.size());
        Assertions.assertEquals("\n" + " bucket[2] = [13, mole3] -> [2, mole2]",hashTable.toString());

        //eliminar un elemento que no exista porque su posicion este vacia
        hashTable.drop("3");
        Assertions.assertEquals(2,hashTable.count());
        Assertions.assertEquals(16,hashTable.size());
        Assertions.assertEquals("\n" + " bucket[2] = [13, mole3] -> [2, mole2]",hashTable.toString());

        //eliminar un elemento que no exita y que su posicion este ocupada por otro que no colisiona
        hashTable.put("1","mole");
        hashTable.drop("12");
        Assertions.assertEquals(3,hashTable.count());
        Assertions.assertEquals(16,hashTable.size());
        Assertions.assertEquals("\n" + " bucket[1] = [1, mole]" + "\n" + " bucket[2] = [13, mole3] -> [2, mole2]",hashTable.toString());

        //eliminar un elemento que no exita y que su posocion este ocupada por tres elementos que colisionan
        hashTable.drop("35");
        Assertions.assertEquals(3,hashTable.count());
        Assertions.assertEquals(16,hashTable.size());
        Assertions.assertEquals("\n" + " bucket[1] = [1, mole]" + "\n" + " bucket[2] = [13, mole3] -> [2, mole2]",hashTable.toString());
    }
}