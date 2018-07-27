package com.waferthin.bitcoinj;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.PostgresFullPrunedBlockStore;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class KmpPostgresFullPrunedBlockStore extends PostgresFullPrunedBlockStore {

    protected List<String> getCreateTablesSQL() {
        List<String> sqlStatements = new ArrayList();
        sqlStatements.add("CREATE TABLE settings (\n    name character varying(32) NOT NULL,\n    value bytea,\n    CONSTRAINT setting_pk PRIMARY KEY (name)\n)\n");
        sqlStatements.add("CREATE TABLE headers (\n    hash bytea NOT NULL,\n    chainwork bytea NOT NULL,\n    height integer NOT NULL,\n    header bytea NOT NULL,\n    wasundoable boolean NOT NULL,\n    CONSTRAINT headers_pk PRIMARY KEY (hash)\n)\n");
        sqlStatements.add("CREATE TABLE undoableblocks (\n    hash bytea NOT NULL,\n    height integer NOT NULL,\n    txoutchanges bytea,\n    transactions bytea,\n    CONSTRAINT undoableblocks_pk PRIMARY KEY (hash)\n)\n");
        sqlStatements.add("CREATE TABLE openoutputs (\n    hash bytea NOT NULL,\n    index integer NOT NULL,\n    height integer NOT NULL,\n    value bigint NOT NULL,\n    scriptbytes bytea NOT NULL,\n    toaddress character varying(35),\n    addresstargetable smallint,\n    coinbase boolean,\n    CONSTRAINT openoutputs_pk PRIMARY KEY (hash,index)\n)\n");

        sqlStatements.add("CREATE TABLE openoutputs_used (\n    hash bytea NOT NULL,\n    index integer NOT NULL,\n    height integer NOT NULL,\n    value bigint NOT NULL,\n    scriptbytes bytea NOT NULL,\n    toaddress character varying(35),\n    addresstargetable smallint,\n    coinbase boolean,\n    CONSTRAINT openoutputs_used_pk PRIMARY KEY (hash,index)\n)\n");
        return sqlStatements;
    }

    protected List<String> getCreateIndexesSQL() {
        List<String> sqlStatements = new ArrayList();
        sqlStatements.add("CREATE INDEX undoableblocks_height_idx ON undoableBlocks USING btree (height)");
        sqlStatements.add("CREATE INDEX openoutputs_hash_index_num_height_toaddress_idx ON openoutputs USING btree (hash, index, height, toaddress)");
        sqlStatements.add("CREATE INDEX openoutputs_addresstargetable_idx ON openoutputs USING btree (addresstargetable)");
        sqlStatements.add("CREATE INDEX openoutputs_hash_idx ON openoutputs USING btree (hash)");
        sqlStatements.add("CREATE INDEX openoutputs_toaddress_idx ON openoutputs USING btree (toaddress)");

        sqlStatements.add("CREATE INDEX openoutputs_used_hash_index_num_height_toaddress_idx ON openoutputs_used USING btree (hash, index, height, toaddress)");
        sqlStatements.add("CREATE INDEX openoutputs_used_addresstargetable_idx ON openoutputs_used USING btree (addresstargetable)");
        sqlStatements.add("CREATE INDEX openoutputs_used_hash_idx ON openoutputs_used USING btree (hash)");
        sqlStatements.add("CREATE INDEX openoutputs_used_toaddress_idx ON openoutputs_used USING btree (toaddress)");

        sqlStatements.add("CREATE TRIGGER \"REMOVE_UTXO\" AFTER DELETE ON public.openoutputs FOR EACH ROW EXECUTE PROCEDURE public.\"REMOVE_UTXO\"()");

        return sqlStatements;
    }

    public KmpPostgresFullPrunedBlockStore(NetworkParameters params, int fullStoreDepth, String hostname, String dbName, String username, String password) throws BlockStoreException {
        super(params, fullStoreDepth, hostname ,dbName, username, password);
    }

    public KmpPostgresFullPrunedBlockStore(NetworkParameters params, int fullStoreDepth, String hostname, String dbName, String username, String password, @Nullable String schemaName) throws BlockStoreException {
        super(params, fullStoreDepth, hostname, dbName, username, password, schemaName);
    }



}
