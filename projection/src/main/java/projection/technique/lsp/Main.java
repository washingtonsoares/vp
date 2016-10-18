package projection.technique.lsp;

import matrix.MatrixFactory;
import matrix.AbstractMatrix;
import distance.dissimilarity.AbstractDissimilarity;
import distance.dissimilarity.DissimilarityFactory;
import distance.dissimilarity.DissimilarityFactory.DissimilarityType;

import java.io.IOException;

public class Main {
    static {
        System.load("/Users/Washington/projects/tcc/libs-vp/liblspsolver.so");
    }
    public static void main(String a[]) throws IOException {
        LSPProjection2D lspProjection2D = new LSPProjection2D();
        String dataSetPath = "/Users/Washington/Dropbox/IC/datasets_v3/B1Q.data";
        DissimilarityType dissimilarityType  = DissimilarityType.EUCLIDEAN;

        AbstractMatrix matrix = MatrixFactory.getInstance(dataSetPath);
        AbstractDissimilarity diss = DissimilarityFactory.getInstance(dissimilarityType);

        AbstractMatrix result = lspProjection2D.project(matrix, diss);
        System.out.println(System.getProperty("java.library.path"));
    }
}
