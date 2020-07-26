package fpt.capstone.bpcrs.model;

import fpt.capstone.bpcrs.constant.BookingEnum;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Booking.class)
public abstract class Booking_ extends fpt.capstone.bpcrs.component.Auditing_ {

	public static volatile SingularAttribute<Booking, Date> from_date;
	public static volatile SingularAttribute<Booking, Date> to_date;
	public static volatile SingularAttribute<Booking, Double> totalPrice;
	public static volatile SingularAttribute<Booking, Car> car;
	public static volatile SingularAttribute<Booking, String> destination;
	public static volatile ListAttribute<Booking, Agreement> agreements;
	public static volatile SingularAttribute<Booking, String> location;
	public static volatile SingularAttribute<Booking, Account> renter;
	public static volatile SingularAttribute<Booking, BookingEnum> status;

	public static final String FROM_DATE = "from_date";
	public static final String TO_DATE = "to_date";
	public static final String TOTAL_PRICE = "totalPrice";
	public static final String CAR = "car";
	public static final String DESTINATION = "destination";
	public static final String AGREEMENTS = "agreements";
	public static final String LOCATION = "location";
	public static final String RENTER = "renter";
	public static final String STATUS = "status";

}

