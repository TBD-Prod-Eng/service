package ro.unibuc.tbd.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.tbd.model.CartRequestDTO;
import ro.unibuc.tbd.model.Client;
import ro.unibuc.tbd.service.ClientService;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{clientId}")
    public Client getClientById(@PathVariable String clientId) {
        return clientService.getClientById(clientId);
    }

    @PostMapping
    public Client registerClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @PostMapping("/{clientId}/cart")
    public Client addToCart(@PathVariable String clientId, @RequestBody CartRequestDTO cartRequest) {
        return clientService.addToCart(clientId, cartRequest);
    }

    @DeleteMapping("/{clientId}/cart")
    public Client removeFromCart(@PathVariable String clientId, @RequestBody CartRequestDTO cartRequest) {
        return clientService.removeFromCart(clientId, cartRequest);
    }

    @GetMapping("/{clientId}/place-order")
    public Client placeOrder(@PathVariable String clientId) {
        return clientService.placeOrder(clientId);
    }

    @PatchMapping("/{clientId}")
    public Client updateClient(@PathVariable String clientId, @RequestBody Client client) {
        return clientService.updateClient(clientId, client);
    }

    @DeleteMapping("/{clientId}")
    public Client deleteClient(@PathVariable String clientId) {
        return clientService.deleteClientById(clientId);
    }

    @DeleteMapping("/clear")
    public void deleteAllClients() {
        clientService.clearAll();
    }
}
