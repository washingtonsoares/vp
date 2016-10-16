/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.stress;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class StressFactory {

    public enum StressType {

        KRUSKAL("Kruskal's stress"),
        NORMALIZED_KRUSKAL("Normalized Kruskal's stress"),
        PARTIAL_NORMALIZED_KRUSKAL("Sampled normalized Kruskal's stress"),
        SAMMON("Sammnon's stress"),
        QUADRATIC_STRESS("Quadratic stress");

        private StressType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
        private final String name;
    }

    public static Stress getInstance(StressType type) {
        switch (type) {
            case KRUSKAL:
                return new KruskalStress();
            case NORMALIZED_KRUSKAL:
                return new NormalizedKruskalStress();
            case PARTIAL_NORMALIZED_KRUSKAL:
                return new PartialNormalizedKruskalStress();
            case SAMMON:
                return new SammonStress();
            case QUADRATIC_STRESS:
                return new QuadraticStress();
        }
        return null;
    }
}
