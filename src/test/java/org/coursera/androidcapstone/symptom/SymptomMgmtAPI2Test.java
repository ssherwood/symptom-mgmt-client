package org.coursera.androidcapstone.symptom;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.coursera.androidcapstone.symptom.model.Doctor;
import org.coursera.androidcapstone.symptom.model.Patient;
import org.coursera.androidcapstone.symptom.oauth.OAuth2RequestInterceptor;
import org.coursera.androidcapstone.symptom.sdr.SDRFindAllResponse;
import org.coursera.androidcapstone.symptom.util.OkHttpClientHelper;
import org.coursera.androidcapstone.symptom.util.RetrofitResponseDecorator;
import org.coursera.androidcapstone.symptom.util.TypedStringHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedString;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.GsonBuilder;

/**
 * 
 */
public class SymptomMgmtAPI2Test {

	private static final String TEST_URL = "https://localhost:8443";

	private	static SymptomMgmtAPI restAdapter;
	private static OAuth2RequestInterceptor oauth2Interceptor;
	
	@BeforeClass
	public static void initializeRestAdapter() throws Exception {

		oauth2Interceptor = new OAuth2RequestInterceptor("android-symptoms-management", "secret");

		restAdapter = new RestAdapter.Builder()
			.setClient(new OkClient(OkHttpClientHelper.getCustomSslOkHttpClient()))
			.setRequestInterceptor(oauth2Interceptor)
			.setConverter(new GsonConverter(Converters.registerAll(new GsonBuilder()).create()))
			.setEndpoint(TEST_URL)
			.setLogLevel(LogLevel.FULL).build()
			.create(SymptomMgmtAPI.class);
	}
	
	@Before
	public void authenticateClient() throws Exception {
		if (oauth2Interceptor.getOAuth2Response() == null) {
			oauth2Interceptor.setOAuth2Response(restAdapter.getAuth("user1", "password1", "password"));
		}
	}
	
	@Test
	public void testGetDoctors() throws Exception {
		SDRFindAllResponse<DoctorResponse> doctors = restAdapter.getDoctors();
		
		assertThat(doctors.getPage().getTotalElements(), is(greaterThan(1)));
		assertThat(doctors.getEmbedded().getDoctors().size(), is(greaterThan(1)));
	}
	
	@Test
	public void testGetDoctor1() throws Exception {		
		Doctor doctor1 = restAdapter.getDoctor(1L);
		
		assertThat(doctor1.getNationalProviderNumber(), is(1234567890));
	}
	
	@Test
	public void testAddDoctor() throws Exception {
	
		Doctor aDoctor = new Doctor();
		aDoctor.setUsername("doc3");
		aDoctor.setNationalProviderNumber(1234567890);
		aDoctor.setFirstName("John");
		aDoctor.setLastName("Doe");
		
		RetrofitResponseDecorator r = new RetrofitResponseDecorator(restAdapter.addDoctor(aDoctor));
		
		assertThat(r.getStatus(), is(HttpStatus.CREATED.value()));
		assertThat(r.getHeader("Location"), is(notNullValue()));
	}
	
	@Test
	public void testPatchDoctorsLastName() throws Exception {
	
		final String lastName = "Howard";
		
		Doctor aDoctor = new Doctor();
		aDoctor.setLastName(lastName); // will only modify the last name
		
		RetrofitResponseDecorator r = new RetrofitResponseDecorator(restAdapter.patchDoctor(1L, aDoctor));
		
		assertThat(r.getStatus(), is(HttpStatus.NO_CONTENT.value()));
		assertThat(restAdapter.getDoctor(1L).getLastName(), is(lastName));
	}	


	@Test
	public void testAssociatePatientToDoctor() throws Exception {		
		RetrofitResponseDecorator r = new RetrofitResponseDecorator(restAdapter.associatePatientToDoctor(1L, TypedStringHelper.build("https://localhost:8443/patients/1", "https://localhost:8443/patients/2")));
		assertThat(r.getStatus(), is(HttpStatus.NO_CONTENT.value()));
	}
	
	
	@Test
	public void testAssociateMedicineToPatient() throws Exception {
		RetrofitResponseDecorator r = new RetrofitResponseDecorator(restAdapter.associateMedicineToPatient(1L, new TypedString("https://localhost:8443/medicine/1")));
		assertThat(r.getStatus(), is(HttpStatus.NO_CONTENT.value()));
	}
	
	@Test
	public void testDisassociateMedicineToPatient() throws Exception {
		RetrofitResponseDecorator r = new RetrofitResponseDecorator(restAdapter.disassociateMedicineToPatient(1L, 1L));
		assertThat(r.getStatus(), is(HttpStatus.NO_CONTENT.value()));
	}

	@Test
	public void testGetPatients() throws Exception {
		SDRFindAllResponse<PatientResponse> patients = restAdapter.getPatients();
		
		assertThat(patients.getPage().getTotalElements(), is(greaterThan(1)));
		assertThat(patients.getEmbedded().getPatients().size(), is(greaterThan(1)));
	}

	@Test
	public void testGetPatient1() throws Exception {		
		Patient patient1 = restAdapter.getPatient(1L);
		
		assertThat(patient1.getFirstName(), is("Jane"));
	}
	
	@Test
	public void testAddPatient() throws Exception {		
		Patient patient3 = new Patient();
		patient3.setUsername("jimmy.smith@nowhere.com");
		patient3.setMedicalId("a1111111");
		patient3.setFirstName("Jimmy");
		patient3.setLastName("Smith");
	
		RetrofitResponseDecorator r = new RetrofitResponseDecorator(restAdapter.addPatient(patient3));
		
		assertThat(r.getStatus(), is(HttpStatus.CREATED.value()));
	}

}