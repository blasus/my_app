package main.java.com.myexercise.contracts;

/**
 * 
 * @author Blasi Francesco
 *
 */

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "duemilaquattordici")
public class Contract {
	
		
	@Id
	private String id;
	
	@Indexed
	@Field("identificativo_lavoratore")
	private String idWorker;
	
	@Field("genere")
	private String gen;
	
	@Field("anno di nascita")
	private int born;
	
	@Field("provincia_domicilio")
	private int provWorker;
	
	@Field("codice_titolo_studio")
	private int tsCode;
	
	@Field("cittadinanza")
	private String nation;
	
	@Indexed
	@Field("identificativo_azienda")
	private String idAgency;
	
	@Field("settore_ateco")
	private double sector;
	
	@Field("provincia_sede_operativa")
	private int provAgency;
	
	@Field("tipologia_contrattuale")
	private String contractType;
	
	@Field("qualifica")
	private String qual;
	
	@Field("tipo_orario")
	private char schedType;
	
	@Field("data_inizio")
	private Calendar startDate;
	
	@Field("data_fine")
	private Calendar endDate;
	
	
	public Contract(){}
	
	@PersistenceConstructor
	public Contract(String idWorker, String gen, int born, int provWorker, int tsCode, String nation,
			String idAgency, double sector, int provAgency, String contractType, String qual,
			String schedType, String startDate, String endDate){
		this.idWorker = idWorker;
		this.gen = gen;
		this.born = born;
		this.provWorker = provWorker;
		this.tsCode = tsCode;
		this.nation = nation;
		this.idAgency = idAgency;
		this.sector = sector;
		this.provAgency = provAgency;
		this.contractType = contractType;
		this.qual = qual;
		this.schedType = schedType.charAt(0);
		this.startDate = new GregorianCalendar(
				Integer.parseInt(startDate.substring(6)),
				Integer.parseInt(startDate.substring(3, 5)),
				Integer.parseInt(startDate.substring(0, 2)));
		if(!endDate.equals("")){
			this.endDate = new GregorianCalendar(
					Integer.parseInt(endDate.substring(6)),
					Integer.parseInt(endDate.substring(3, 5)),
					Integer.parseInt(endDate.substring(0, 2)));
		}
	}
	
	public String getWorker() {
		return idWorker;
	}

	public void setWorker(String id_l) {
		this.idWorker = id_l;
	}

	public String getGen() {
		return gen;
	}

	public void setGen(String gen) {
		this.gen = gen;
	}

	public int getBorn() {
		return born;
	}
	
	public void setBorn(int born) {
		this.born = born;
	}

	public int getProv_w() {
		return provWorker;
	}
	
	public void setProv_w(int prov_l) {
		this.provWorker = prov_l;
	}

	public int getTs_code() {
		return tsCode;
	}
	
	public void setTs_code(int ts_code) {
		this.tsCode = ts_code;
	}

	public String getNation() {
		return nation;
	}
	
	public void setNation(String nation) {
		this.nation = nation;
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
		return provAgency;
	}
	
	public void setProv_a(int prov_a) {
		this.provAgency = prov_a;
	}

	public String getContract_type() {
		return contractType;
	}
	
	public void setContract_type(String contract_type) {
		this.contractType = contract_type;
	}

	public String getQual() {
		return qual;
	}
	
	public void setQual(String qual) {
		this.qual = qual;
	}

	public char getSched_type() {
		return schedType;
	}
	
	public void setSched_type(char sched_type) {
		this.schedType = sched_type;
	}

	public Calendar getStart() {
		return startDate;
	}
	
	public void setStart(Calendar start) {
		this.startDate = start;
	}

	public Calendar getEnd() {
		return endDate;
	}
	
	public void setEnd(Calendar end) {
		this.endDate = end;
	}
	
	@Override
	public String toString() {
		return "{ \"identificativo_lavoratore\" = \"" + idWorker + "\", \"genere\" = \"" + gen + "\", \"anno_nascita\" = \"" + born 
				+ "\", \"provincia_domicilio\" = \"" + provWorker + "\", \"codice_titolo_studio\" = \"" + tsCode + "\", \"cittadinanza\" = \"" + nation 
				+ "\", \"identificativo_azienda\" = \"" + idAgency + "\", \"settore_ateco\" = \"" + sector + "\", \"provincia_sede_operativa\" = \"" + provAgency
				+ "\", \"tipologia_contrattuale\" = \"" + contractType + "\", \"qualifica\" = \"" + qual + "\", \"tipo_orario\" = \"" + schedType 
				+ "\", \"data_inizio\" = \"" + startDate.toString() + "\", \"data_fine\" = \"" + endDate.toString() + "\"}\n";
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
		if (born != other.born)
			return false;
		if (contractType == null) {
			if (other.contractType != null)
				return false;
		} else if (!contractType.equals(other.contractType))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (gen == null) {
			if (other.gen != null)
				return false;
		} else if (!gen.equals(other.gen))
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
		if (nation == null) {
			if (other.nation != null)
				return false;
		} else if (!nation.equals(other.nation))
			return false;
		if (provAgency != other.provAgency)
			return false;
		if (provWorker != other.provWorker)
			return false;
		if (qual == null) {
			if (other.qual != null)
				return false;
		} else if (!qual.equals(other.qual))
			return false;
		if (schedType != other.schedType)
			return false;
		if (Double.doubleToLongBits(sector) != Double.doubleToLongBits(other.sector))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (tsCode != other.tsCode)
			return false;
		return true;
	}

}
