package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.exception.BadRequestException;
import ProjektZespolowySpring.exception.NotFoundException;
import ProjektZespolowySpring.model.borrow.BorrowDTO;

import ProjektZespolowySpring.service.BorrowService;
import ProjektZespolowySpring.service.ReservationService;
import ProjektZespolowySpring.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class BorrowController {

    private BorrowService borrowService;
    private ReservationService reservationService;

    @Autowired
    public BorrowController(BorrowService borrowService, ReservationService reservationService) {
        this.borrowService = borrowService;
        this.reservationService = reservationService;
    }

    @PostMapping("/borrows")
    public ResponseEntity<Resource<BorrowDTO>> addBorrow(@RequestBody BorrowDTO borrowDTO, BindingResult result) {
        checkPostErrors(borrowDTO, result);
        BorrowDTO dto = borrowService.add(borrowDTO);
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/borrows/" + dto.getId()))
                .body(toResource(dto));
    }

    @GetMapping("/borrows")
    public Resources<Resource<BorrowDTO>> getBorrows() {
        return toResources(borrowService.findAll());
    }

    @GetMapping("/borrows/{id}")
    public Resource<BorrowDTO> getBorrow(@PathVariable int id) {
        return toResource(borrowService.findById(id).orElseThrow(NotFoundException::new));
    }

    @PutMapping("/borrows/{id}")
    public ResponseEntity<Resource<BorrowDTO>> updtadeBorrow(@PathVariable int id, @RequestBody @Valid BorrowDTO borrowDTO, BindingResult result) {
        checkPutErrors(borrowDTO, result, id);
        BorrowDTO dto = borrowService.update(id, borrowDTO);
        return ResponseEntity.ok().body(toResource(dto));
    }

    @DeleteMapping("/borrows/{id}")
    public ResponseEntity<?> deleteBorrow(@PathVariable int id) {
        checkDeleteErrors(id);
        borrowService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private void checkPostErrors(BorrowDTO borrowDTO, BindingResult result) {
        badRequest(result);
        if (!reservationService.existById(borrowDTO.getReservationId())) {
            throw new BadRequestException("This reservation does not exist");
        }
        sameReservation(borrowDTO.getReservationId());
    }

    private void checkPutErrors(BorrowDTO borrowDTO, BindingResult result, int id) {
        badRequest(result);
        notFound(id);
    }

    private void checkDeleteErrors(int id) {
        notFound(id);
    }

    private void notFound(int id) {
        if (!borrowService.existsById(id)) {
            throw new NotFoundException();
        }
    }

    private void badRequest(BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(Util.getErrorMessage(result));
        }
    }

    private void sameReservation(int reservationId) {
        List<BorrowDTO> listOfBorrows = borrowService.findAll();
        for (BorrowDTO dto : listOfBorrows) {
            if (dto.getReservationId() == reservationId) {
                throw new BadRequestException("This book is borrowed");
            }
        }
    }

    private Resource<BorrowDTO> toResource(BorrowDTO dto) {
        return new Resource<>(dto,
                linkTo(BorrowController.class).slash("borrows/" + dto.getId()).withSelfRel(),
                linkTo(BorrowController.class).slash("borrows/" + dto.getId()).withRel("borrow"));
    }

    private Resources<Resource<BorrowDTO>> toResources(List<BorrowDTO> list) {
        return new Resources<>(list.stream().map(this::toResource).collect(Collectors.toList()),
                linkTo(BorrowController.class).slash("borrows").withSelfRel());
    }

}
