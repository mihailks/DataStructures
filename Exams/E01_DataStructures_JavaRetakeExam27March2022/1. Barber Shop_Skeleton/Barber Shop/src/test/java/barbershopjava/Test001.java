package barbershopjava;

import barbershopjava.Barber;
import barbershopjava.BarberShopImpl;
import barbershopjava.Client;
import barbershopjava.Gender;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class Test001 {

    private BarberShopImpl barberShop;
    private Barber b1 = new Barber("b1", 1, 1);
    private Barber b2 = new Barber("b2", 1, 1);
    private Barber b3 = new Barber("b3", 1, 1);
    private Barber b4 = new Barber("b4", 1, 1);

    private Client c1 = new Client("c1", 10, Gender.MALE);
    private Client c2 = new Client("c2", 11, Gender.MALE);
    private Client c3 = new Client("c3", 12, Gender.MALE);
    private Client c4 = new Client("c4", 12, Gender.MALE);

    @Before
    public void Setup() {
        this.barberShop = new BarberShopImpl();

        this.barberShop.addBarber(b1);
        this.barberShop.addBarber(b2);
        this.barberShop.addBarber(b3);
        this.barberShop.addBarber(b4);
        this.barberShop.addClient(c1);
        this.barberShop.addClient(c2);
        this.barberShop.addClient(c3);
        this.barberShop.addClient(c4);

        barberShop.assignClient(b2,c1);
        barberShop.assignClient(b3,c2);
        barberShop.assignClient(b3,c3);

    }

    @Test
    public void TestAddBarber() {
        this.barberShop.addBarber(b1);
        assertTrue(this.barberShop.exist(b1));
    }

    @Test
    public void testGetClientsWithNoBarber(){

        Collection<Client> clientsWithNoBarber = barberShop.getClientsWithNoBarber();
        System.out.println();
    }

    @Test
    public void AllBarbersSortedWithClientsCountDesc(){
        Collection<Barber> allBarbersSortedWithClientsCountDesc = barberShop.getAllBarbersSortedWithClientsCountDesc();
        System.out.println();
    }


}
