package projection.technique.lsp;

import com.google.gson.Gson;
import matrix.MatrixFactory;
import matrix.AbstractMatrix;
import distance.dissimilarity.AbstractDissimilarity;
import distance.dissimilarity.DissimilarityFactory;
import distance.dissimilarity.DissimilarityFactory.DissimilarityType;
import matrix.dense.DenseMatrix;
import matrix.sparse.SparseMatrix;
import projection.technique.projclus.ProjClusProjection;

import javax.servlet.MultipartConfigElement;

import static spark.Spark.*;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String a[]) throws IOException {
        //System.out.println(System.getProperty("java.library.path"));

        Gson gson = new Gson();

        get("/matriz/:dissimilarity-type/:projection-type", "application/json", (req, res) -> {
            res.header("Content-Encoding", "gzip");
            String projectionType =  req.params(":projection-type");
            System.out.println(projectionType);
            String dissimilarityTypeParam =  req.params(":dissimilarity-type");
            AbstractMatrix result = makeAbstractMatrix(dissimilarityTypeParam);

            return  result;
        }, gson::toJson);

        post("/upload-dataset", (request, response) -> {
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            AbstractMatrix result;
            try (InputStream is = request.raw().getPart("dataset").getInputStream()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                System.out.println("");
            }
            return "file uploaded";
        });

        enableCORS("*","*","*");
    }

    static AbstractMatrix makeAbstractMatrix(String dissimilarityTypeParam)  throws IOException {
        LSPProjection2D lspProjection2D = new LSPProjection2D();
        ProjClusProjection projClusProjection = new ProjClusProjection();
        String dataSetPath = "/Users/Washington/Dropbox/IC/datasets_v3/B2Q.data";
        DissimilarityType dissimilarityType  = DissimilarityType.valueOf(dissimilarityTypeParam);
        AbstractMatrix matrix = MatrixFactory.getInstance(dataSetPath);
        AbstractDissimilarity diss = DissimilarityFactory.getInstance(dissimilarityType);

        return projClusProjection.project(matrix, diss);
    }



    static void enableCORS(final String origin, final String methods, final String headers) {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }
}
