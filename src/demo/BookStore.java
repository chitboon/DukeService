package demo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/books")
public class BookStore {
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<BookDetail> getBooks() {
        BookDAO dao = new BookDAO();
        List<BookDetail> books = dao.getAllBook();
        return books;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public BookDetail getBook(@PathParam("id") String bookId) {
        System.out.println("bookId " + bookId);
        BookDAO dao = new BookDAO();
        BookDetail book = dao.getBookDetails(bookId);
        return book;
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public String put(BookDetail book) {
        System.out.println(book);
        BookDAO dao = new BookDAO();
        dao.add(book);
        return "ok";
    }

}
