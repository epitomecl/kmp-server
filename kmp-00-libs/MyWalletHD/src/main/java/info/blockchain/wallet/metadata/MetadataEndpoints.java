package info.blockchain.wallet.metadata;

import info.blockchain.wallet.metadata.data.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.List;

public interface MetadataEndpoints {

    ///////////////////////////////////////////////////////////////////////////
    // AUTH
    ///////////////////////////////////////////////////////////////////////////

    @GET(MetadataUrls.AUTH)
    Call<Auth> getNonce();

    @POST(MetadataUrls.AUTH)
    Call<Auth> getToken(@Body HashMap<String, String> body);


    ///////////////////////////////////////////////////////////////////////////
    // TRUSTED
    ///////////////////////////////////////////////////////////////////////////

    @GET(MetadataUrls.TRUSTED)
    Call<Trusted> getTrustedList(@Header("Authorization") String jwToken);

    @GET(MetadataUrls.TRUSTED)
    Call<Trusted> getTrusted(@Header("Authorization") String jwToken, @Query("mdid") String mdid);

    @PUT(MetadataUrls.TRUSTED + "/{mdid}")
    Call<Trusted> putTrusted(@Header("Authorization") String jwToken, @Path("mdid") String mdid);

    @DELETE(MetadataUrls.TRUSTED + "/{mdid}")
    Call<ResponseBody> deleteTrusted(@Header("Authorization") String jwToken, @Path("mdid") String mdid);


    ///////////////////////////////////////////////////////////////////////////
    // MESSAGES
    ///////////////////////////////////////////////////////////////////////////

    @POST(MetadataUrls.MESSAGES)
    Call<Message> postMessage(@Header("Authorization") String jwToken, @Body Message body);

    @GET(MetadataUrls.MESSAGES)
    Call<List<Message>> getMessages(@Header("Authorization") String jwToken, @Query("new") Boolean onlyProcessed);

    @GET(MetadataUrls.MESSAGE + "/{uuid}")
    Call<Message> getMessage(@Header("Authorization") String jwToken, @Path("uuid") String messageId);

    @PUT(MetadataUrls.MESSAGE + "/{uuid}/processed")
    Call<Void> processMessage(@Header("Authorization") String jwToken, @Path("uuid") String id, @Body MessageProcessRequest body);


    ///////////////////////////////////////////////////////////////////////////
    // SHARING
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Arbitrary JSON can be sent here, but for now we're not using it for anything so an empty
     * JsonObject can be sent.
     */
    @POST(MetadataUrls.SHARE)
    Call<Invitation> postShare(@Header("Authorization") String jwToken, @Body String jsonData);

    @POST(MetadataUrls.SHARE + "/{uuid}")
    Call<Invitation> postToShare(@Header("Authorization") String jwToken, @Path("uuid") String uuid, @Body String jsonData);

    @GET(MetadataUrls.SHARE + "/{uuid}")
    Call<Invitation> getShare(@Header("Authorization") String jwToken, @Path("uuid") String uuid);

    @DELETE(MetadataUrls.SHARE + "/{uuid}")
    Call<Invitation> deleteShare(@Header("Authorization") String jwToken, @Path("uuid") String uuid);


    ///////////////////////////////////////////////////////////////////////////
    // CRUD OPERATIONS
    ///////////////////////////////////////////////////////////////////////////

    @PUT(MetadataUrls.METADATA + "/{addr}")
    Call<Void> putMetadata(@Path("addr") String address, @Body MetadataRequest body);

    @GET(MetadataUrls.METADATA + "/{addr}")
    Call<MetadataResponse> getMetadata(@Path("addr") String address);

    @DELETE(MetadataUrls.METADATA + "/{addr}")
    Call<Void> deleteMetadata(@Path("addr") String address, @Query("signature") String signature);
}
