package controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.QGram;

public class QGramTest {
    @Test
    void QGramSimilarity() {
        QGram qGram1=new QGram(3);
        Assertions.assertEquals("0,5714", String.format("%.4f", qGram1.similarity("museo", "muceo")));
        Assertions.assertEquals("0,5455", String.format("%.4f", qGram1.similarity("momo", "mom")));
        QGram qGram2=new QGram(2);
        Assertions.assertEquals("0,8000", String.format("%.4f", qGram2.similarity("museo uba", "muceo uba")));
        Assertions.assertEquals("0,6364", String.format("%.4f", qGram2.similarity("viblioteka", "biblioteca")));
    }

}