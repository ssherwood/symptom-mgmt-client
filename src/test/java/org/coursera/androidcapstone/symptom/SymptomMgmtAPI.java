package org.coursera.androidcapstone.symptom;

import org.coursera.androidcapstone.symptom.model.Doctor;
import org.coursera.androidcapstone.symptom.model.Patient;
import org.coursera.androidcapstone.symptom.oauth.OAuth2Response;
import org.coursera.androidcapstone.symptom.sdr.SDRFindAllResponse;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.mime.TypedString;

public interface SymptomMgmtAPI {

	final String TOKEN_PATH = "/oauth/token";

	@FormUrlEncoded
	@POST(TOKEN_PATH)
	public OAuth2Response getAuth(@Field("username") String username, @Field("password") String password, @Field("grant_type") String grantType);
	
	
	@GET("/doctors")
	public SDRFindAllResponse<DoctorResponse> getDoctors();
	
	@GET("/doctors/{id}")
	public Doctor getDoctor(@Path("id") Long id);
	
	@POST("/doctors")
	public Response addDoctor(@Body Doctor doctor);
	
	@PATCH("/doctors/{id}")
	public Response patchDoctor(@Path("id") Long id, @Body Doctor doctor);
	
	@Headers({ "Content-type: text/uri-list"})
	@PUT("/doctors/{id}/patients")
	public Response associatePatientToDoctor(@Path("id") Long id, @Body TypedString hrefs);


	@GET("/patients")
	public SDRFindAllResponse<PatientResponse> getPatients();

	@POST("/patients")
	public Response addPatient(@Body Patient patient);
	
	@GET("/patients/{id}")
	public Patient getPatient(@Path("id") Long id);
	
	@PATCH("/patients/{id}")
	public Response patchPatient(@Path("id") Long id, @Body Patient patient);
	
	@Headers({ "Content-type: text/uri-list"})
	@PUT("/patients/{id}/medicine")
	public Response associateMedicineToPatient(@Path("id") Long id, @Body TypedString hrefs);
	
	@Headers({ "Content-type: text/uri-list"})
	@DELETE("/patients/{id}/medicine/{medicineId}")
	public Response disassociateMedicineToPatient(@Path("id") Long id, @Path("medicineId") Long medicineId);
}
