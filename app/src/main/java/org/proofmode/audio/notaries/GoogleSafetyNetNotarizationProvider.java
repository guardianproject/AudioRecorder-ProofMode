package org.proofmode.audio.notaries;

import static org.witness.proofmode.ProofMode.GOOGLE_SAFETYNET_FILE_TAG;

import android.content.Context;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.witness.proofmode.notarization.NotarizationListener;
import org.witness.proofmode.notarization.NotarizationProvider;

import java.io.IOException;
import java.io.InputStream;

import timber.log.Timber;

public class GoogleSafetyNetNotarizationProvider implements NotarizationProvider {

    private final Context mContext;

    public GoogleSafetyNetNotarizationProvider (Context context)
    {
        mContext = context;
    }

    @Override
    public void notarize(String mediaHash, String mimeType, InputStream is, NotarizationListener listener) {

        new SafetyNetCheck().sendSafetyNetRequest(mContext, mediaHash, new OnSuccessListener<SafetyNetApi.AttestationResponse>() {
            @Override
            public void onSuccess(SafetyNetApi.AttestationResponse response) {
                // Indicates communication with the service was successful.
                // Use response.getJwsResult() to get the result data.

                listener.notarizationSuccessful(mediaHash, response.getJwsResult());
           //     Timber.d("Success! SafetyNet result: isBasicIntegrity: " + isBasicIntegrity + " isCts:" + isCtsMatch);
//                writeProof(context, uriMedia, mediaHash, showDeviceIds, showLocation, showMobileNetwork, resultString, isBasicIntegrity, isCtsMatch, timestamp, null);



            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // An error occurred while communicating with the service.
                Timber.d(e,"SafetyNet check failed");
                listener.notarizationFailed(-1,e.getMessage());
            }
        });
    }


    public SafetyNetResponse parseJsonWebSignature(String jwsResult) {
        if (jwsResult == null) {
            return null;
        }
        //the JWT (JSON WEB TOKEN) is just a 3 base64 encoded parts concatenated by a . character
        final String[] jwtParts = jwsResult.split("\\.");

        if (jwtParts.length == 3) {
            //we're only really interested in the body/payload
            String decodedPayload = new String(Base64.decode(jwtParts[1], Base64.DEFAULT));

            return SafetyNetResponse.parse(decodedPayload);
        } else {
            return null;
        }
    }

    @Override
    public String getProof(String hash) throws IOException {
        return null;
    }


    @Override
    public String getNotarizationFileExtension () {
        return GOOGLE_SAFETYNET_FILE_TAG;
    }
}
