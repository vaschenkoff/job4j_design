package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoxTest {

    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere");
    }

    @Test
    void isThisTetrahedron() {
        Box box = new Box(4, 5);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Tetrahedron")
                .doesNotContain("Sphere")
                .doesNotContain("Cube")
                .containsIgnoringCase("tetrahedron")
                .isNotEmpty();
    }

    @Test
    void isThisCube() {
        Box box = new Box(8, 15);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Cube")
                .doesNotContain("Tetrahedron")
                .doesNotContain("Sphere")
                .containsIgnoringCase("cube")
                .isNotEmpty();
    }

    @Test
    void isThisUnknown() {
        Box box = new Box(8, 0);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Unknown object")
                .isNotEmpty()
                .doesNotContain("Cube")
                .doesNotContain("Sphere")
                .doesNotContain("Tetrahedron")
                .containsIgnoringCase("unknown")
                .containsIgnoringCase("object")
                .contains("Unknown", "object");
    }

    @Test
    void isThisUnknown2() {
        Box box = new Box(15, 8);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Unknown object")
                .isNotEmpty()
                .doesNotContain("Cube")
                .doesNotContain("Sphere")
                .doesNotContain("Tetrahedron")
                .containsIgnoringCase("unknown")
                .containsIgnoringCase("object")
                .contains("Unknown", "object");
    }

    @Test
    void if0Vertex() {
        Box box = new Box(0, 15);
        int vertex = box.getNumberOfVertices();
        assertThat(vertex).isEqualTo(0).
                isLessThan(1)
                .isZero();
    }

    @Test
    void if4Vertex() {
        Box box = new Box(4, 15);
        int vertex = box.getNumberOfVertices();
        assertThat(vertex).isEqualTo(4)
                .isPositive()
                .isLessThan(5)
                .isGreaterThan(3)
                .isNotZero();
    }

    @Test
    void if8Vertex() {
        Box box = new Box(8, 15);
        int vertex = box.getNumberOfVertices();
        assertThat(vertex).isEqualTo(8)
                .isPositive()
                .isLessThan(10)
                .isGreaterThan(7)
                .isNotZero();
    }

    @Test
    void ifExist() {
        Box box = new Box(0, 15);
        boolean exist = box.isExist();
        assertThat(exist).isTrue();
        Box box2 = new Box(-1, 15);
        boolean notExist = box2.isExist();
        assertThat(notExist).isFalse();
    }

    @Test
    void getSphereArea() {
        Box box = new Box(0, 5);
        double rsl = box.getArea();
        assertThat(rsl).isCloseTo(314.15d, withPrecision(0.01d))
                .isCloseTo(314.15d, withinPercentage(1.0d))
                .isGreaterThan(314.0d)
                .isLessThan(314.16d);
    }

    @Test
    void getTetrahedronArea() {
        Box box = new Box(4, 5);
        double rsl = box.getArea();
        assertThat(rsl).isCloseTo(43.30d, withPrecision(0.01d))
                .isCloseTo(43.30d, withinPercentage(1.0d))
                .isGreaterThan(43.29d)
                .isLessThan(43.31d);
    }

    @Test
    void getCubeArea() {
        Box box = new Box(8, 5);
        double rsl = box.getArea();
        assertThat(rsl).isCloseTo(150d, withPrecision(0.01d))
                .isCloseTo(150d, withinPercentage(1.0d))
                .isGreaterThan(149d)
                .isLessThan(151d)
                .isEqualTo(150d);
    }

    @Test
    void getUnknownArea() {
        Box box = new Box(10, 5);
        double rsl = box.getArea();
        assertThat(rsl).isEqualTo(0d);
    }
}