package com.myexercise.contracts;

/**
 * @author Blasi Francesco
 * @version 0.0.1-SNAPSHOT
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "duemilaquattordici")
@CompoundIndexes({
	@CompoundIndex(name = "Contract_TextIndex", def = "{'identificativo_lavoratore': 'text', 'cittadinanza': 'text', "
			+ "'identificativo_azienda': 'text', 'qualifica': 'text', 'tipologia_contrattuale': 'text', "
			+ "'anno_nascita': 1, 'settore_ateco': 1, 'data_inizio': 1, 'data_fine': 1}")
})
public class Contract {
	
	@Id
	private ObjectId _id;
	
	@Indexed
	@Field("id")
	private int id;
	
	@Field("identificativo_lavoratore")
	private String idWorker;
	
	@Field("genere")
	private String genre;
	
	@Field("anno_nascita")
	private int birth;
	
	@Field("provincia_domicilio")
	private int resWorker;
	
	@Field("codice_titolo_studio")
	private int studyCode;
	
	@Field("cittadinanza")
	private String citizenship;
	
	@Field("identificativo_azienda")
	private String idAgency;
	
	@Field("settore_ateco")
	private double sector;
	
	@Field("provincia_sede_operativa")
	private int resAgency;
	
	@Field("tipologia_contrattuale")
	private String cType;
	
	@Field("qualifica")
	private String title;
	
	@Field("tipo_orario")
	private String schedType;
	
	@Field("data_inizio")
	private Date startDate;
	
	@Field("data_fine")
	private Date endDate;
	
	
	/**
	 * Class constructor.
	 */
	
	public Contract(){}
	
	
	/**
	 * Class constructor describing a contract object characterized from every property as the same as it stored in DB.
	 * Spring mongodb repository will use this costructor to map every entry in a contract object.
	 * Every contract has a numeric univocal id, a bunch of informations about the subjects stipulated the contract:
	 * 
	 * Worker:
	 * <ul>	
	 * <li> its identificative code, in the structure of "PF" followed by at least one number;
	 * <li> genre, corresponding to its genre (M for male and F for female);
	 * <li> birth year;
	 * <li> province of residence, characterized from an integer;
	 * <li> a classification of its studies;
	 * <li> citizenship, a string with domain {ITALIANA, UE, NON UE}.
	 * </ul>  
	 * Agency:
	 * <ul>	
	 * <li> its identificative code, in the structure of "PG" followed by at least one number;  
	 * <li> ateco sector - see {@link https://it.wikipedia.org/wiki/ATECO};
	 * <li> province of residence, characterized from an integer.
	 * </ul>
	 *  
	 * And more informations relating to the characteristics of the specific contract, like its type, the date of its validation, etc.
	 * 
	 * 
	 * @param id  univocal integer given to every contract
	 * @param idWorker  a string representing the worker identificative code  
	 * @param genre  the worker genre
	 * @param birth  the worker year of birth
	 * @param resWorker  the worker province of residence
	 * @param studyCode  the worker study code
	 * @param citizenship  the worker citizenship 
	 * @param idAgency  a string representing the agency identificative code
	 * @param sector  the ateco sector
	 * @param resAgency  the agency province of residence
	 * @param cType  the contract type, described from an alphabetic char
	 * @param title  a code composed from six numbers delimited from dots (n.n.n.n.n.n with n one or more numbers)
	 * @param schedType  the working hours scheduled in a contract. It's described from an alphabetic char
	 * @param startDate  the date of contract validation (object date)
	 * @param endDate  the date, if exists, of contract expiration
	 */
	@PersistenceConstructor
	public Contract(int id, String idWorker, String genre, int birth, int resWorker, int studyCode, String citizenship,
			String idAgency, double sector, int resAgency, String cType, String title,
			String schedType, Date startDate, Date endDate){
		this.id = id;
		this.idWorker = idWorker;
		this.genre = genre;
		this.birth = birth;
		this.resWorker = resWorker;
		this.studyCode = studyCode;
		this.citizenship = citizenship;
		this.idAgency = idAgency;
		this.sector = sector;
		this.resAgency = resAgency;
		this.cType = cType;
		this.title = title;
		this.schedType = schedType;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	/* 
	 * getter and setter methods:
	 */
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getWorker() {
		return idWorker;
	}

	public void setWorker(String id_l) {
		this.idWorker = id_l;
	}

	public String getGen() {
		return genre;
	}

	public void setGen(String gen) {
		this.genre = gen;
	}

	public int getBorn() {
		return birth;
	}
	
	public void setBorn(int born) {
		this.birth = born;
	}

	public int getProv_w() {
		return resWorker;
	}
	
	public void setProv_w(int prov_l) {
		this.resWorker = prov_l;
	}

	public int getTs_code() {
		return studyCode;
	}
	
	public void setTs_code(int ts_code) {
		this.studyCode = ts_code;
	}

	public String getNation() {
		return citizenship;
	}
	
	public void setNation(String nation) {
		this.citizenship = nation;
	}

	public String getId_a() {
		return idAgency;
	}
	
	public void setId_a(String id_a) {
		this.idAgency = id_a;
	}

	public double getSector() {
		return sector;
	}
	
	public void setSector(double sector) {
		this.sector = sector;
	}

	public int getProv_a() {
		return resAgency;
	}
	
	public void setProv_a(int prov_a) {
		this.resAgency = prov_a;
	}

	public String getContract_type() {
		return cType;
	}
	
	public void setContract_type(String contract_type) {
		this.cType = contract_type;
	}

	public String getQual() {
		return title;
	}
	
	public void setQual(String qual) {
		this.title = qual;
	}

	public String getSched_type() {
		return schedType;
	}
	
	public void setSched_type(String sched_type) {
		this.schedType = sched_type;
	}

	public Date getStart() {
		return startDate;
	}
	
	public void setStart(Date start) {
		this.startDate = start;
	}

	public Date getEnd() {
		return endDate;
	}
	
	public void setEnd(Date end) {
		this.endDate = end;
	}
	
	//useful methods from Object class
	
	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String json= "{\"Id\" : \"" + id + "\", \"idWorker\" : \"" + idWorker + "\", \"genre\" : \"" + genre + "\", \"birth\" : " + birth 
				+ ", \"resWorker\" : " + resWorker + ", \"studyCode\" : " + studyCode + ", \"citizenship\" : \"" + citizenship 
				+ "\", \"idAgency\" : \"" + idAgency + "\", \"sector\" : " + sector + ", \"resAgency\" : " + resAgency
				+ ", \"cType\" : \"" + cType + "\", \"title\" = \"" + title + "\", \"schedType\" : \"" + schedType 
				+ "\", \"startDate\" : \"" + formatter.format(startDate);
		if(endDate != null) json += "\", \"endDate\" : \"" + formatter.format(endDate);
		
		return json += "\"}";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contract other = (Contract) obj;
		if (id != other.id)
			return false;
		if (birth != other.birth)
			return false;
		if (cType == null) {
			if (other.cType != null)
				return false;
		} else if (!cType.equals(other.cType))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (idAgency == null) {
			if (other.idAgency != null)
				return false;
		} else if (!idAgency.equals(other.idAgency))
			return false;
		if (idWorker == null) {
			if (other.idWorker != null)
				return false;
		} else if (!idWorker.equals(other.idWorker))
			return false;
		if (citizenship == null) {
			if (other.citizenship != null)
				return false;
		} else if (!citizenship.equals(other.citizenship))
			return false;
		if (resAgency != other.resAgency)
			return false;
		if (resWorker != other.resWorker)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (schedType == null) {
			if (other.schedType != null)
				return false;
		} else if (!schedType.equals(other.schedType))
			return false;
		if (Double.doubleToLongBits(sector) != Double.doubleToLongBits(other.sector))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (studyCode != other.studyCode)
			return false;
		return true;
	}

}
