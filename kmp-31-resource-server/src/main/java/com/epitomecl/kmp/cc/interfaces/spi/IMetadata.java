package com.epitomecl.kmp.cc.interfaces.spi;

import info.blockchain.wallet.metadata.MetadataUrls;
import info.blockchain.wallet.metadata.data.*;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Header;

import java.util.HashMap;
import java.util.List;

public interface IMetadata {

    ///////////////////////////////////////////////////////////////////////////
    // AUTH
    ///////////////////////////////////////////////////////////////////////////

    @GetMapping("/" + MetadataUrls.AUTH)
    Auth getNonce();

    @PostMapping("/" + MetadataUrls.AUTH)
    Auth getToken(@RequestBody HashMap<String, String> body);


    ///////////////////////////////////////////////////////////////////////////
    // TRUSTED
    ///////////////////////////////////////////////////////////////////////////

    @GetMapping("/" + MetadataUrls.TRUSTED)
    Trusted getTrustedList(@Header("Authorization") String jwToken);

    @GetMapping("/" + MetadataUrls.TRUSTED)
    Trusted getTrusted(@Header("Authorization") String jwToken, @RequestParam("mdid") String mdid);

    @PutMapping("/" + MetadataUrls.TRUSTED + "/{mdid}")
    Trusted putTrusted(@Header("Authorization") String jwToken, @PathVariable("mdid") String mdid);

    @DeleteMapping("/" + MetadataUrls.TRUSTED + "/{mdid}")
    String deleteTrusted(@Header("Authorization") String jwToken, @PathVariable("mdid") String mdid);


    ///////////////////////////////////////////////////////////////////////////
    // MESSAGES
    ///////////////////////////////////////////////////////////////////////////

    @PostMapping("/" + MetadataUrls.MESSAGES)
    Message postMessage(@Header("Authorization") String jwToken, @RequestBody Message body);

    @GetMapping("/" + MetadataUrls.MESSAGES)
    List<Message> getMessages(@Header("Authorization") String jwToken, @RequestParam("new") Boolean onlyProcessed);

    @GetMapping("/" + MetadataUrls.MESSAGE + "/{uuid}")
    Message getMessage(@Header("Authorization") String jwToken, @PathVariable("uuid") String messageId);

    @PutMapping("/" + MetadataUrls.MESSAGE + "/{uuid}/processed")
    Void processMessage(@Header("Authorization") String jwToken, @PathVariable("uuid") String id, @RequestBody MessageProcessRequest body);


    ///////////////////////////////////////////////////////////////////////////
    // SHARING
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Arbitrary JSON can be sent here, but for now we're not using it for anything so an empty
     * JsonObject can be sent.
     */
    @PostMapping("/" + MetadataUrls.SHARE)
    Invitation postShare(@Header("Authorization") String jwToken, @RequestBody String jsonData);

    @PostMapping("/" + MetadataUrls.SHARE + "/{uuid}")
    Invitation postToShare(@Header("Authorization") String jwToken, @PathVariable("uuid") String uuid, @RequestBody String jsonData);

    @GetMapping("/" + MetadataUrls.SHARE + "/{uuid}")
    Invitation getShare(@Header("Authorization") String jwToken, @PathVariable("uuid") String uuid);

    @DeleteMapping("/" + MetadataUrls.SHARE + "/{uuid}")
    Invitation deleteShare(@Header("Authorization") String jwToken, @PathVariable("uuid") String uuid);


    ///////////////////////////////////////////////////////////////////////////
    // CRUD OPERATIONS
    ///////////////////////////////////////////////////////////////////////////

    @PutMapping("/" + MetadataUrls.METADATA + "/{addr}")
    Void putMetadata(@PathVariable("addr") String address, @RequestBody MetadataRequest body);

    @GetMapping("/" + MetadataUrls.METADATA + "/{addr}")
    MetadataResponse getMetadata(@PathVariable("addr") String address);

    @DeleteMapping("/" + MetadataUrls.METADATA + "/{addr}")
    Void deleteMetadata(@PathVariable("addr") String address, @RequestParam("signature") String signature);
}
