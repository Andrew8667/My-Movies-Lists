import {
  Container,
  Typography,
  IconButton,
  Box,
  TextField,
  Button,
  Modal,
  Popover,
  Alert,
  Rating,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import InfoIcon from "@mui/icons-material/Info";
import React, { useEffect, useState } from "react";
import axios from "axios";
import NavigationBar from "../components/NavigationBar";

/**
 * This screen contains option for user's to create their own movie lists that can be edited and deleted
 * In each list, you can see the poster of the movies you've rated and their star rating underneath the poster
 * Clicking on the poster will allow you to edit the details of the rating
 * @returns a screen containing user's movie lists
 */
const Lists = function Lists() {
  const [listModal, setListModal] = useState(false); //visibilty of modal for creating new list
  const [isCreate, setIsCreate] = useState(true); //decides what the modal dealing with list should display: edit or delete screen
  const [listName, setListName] = useState(""); //the name of the list to be added
  const [description, setDescription] = useState(""); //the description of the list
  const [statusModalOpen, setStatusModalOpen] = useState(false); //used to control visibility of feedback after trying to create list
  const [isSuccessful, setIsSuccessful] = useState(false); //create list can be successful or not
  const [categoriesMoviesReviews, setCategoriesMoviesReviews] = useState([]); //dictionary containing the categories, the movies inside each category, and their ratings
  const [listDescriptionEl, setListDescriptionEl] = useState(null); //the element the list description will be attached to
  const [displayedDescription, setDisplayedDescription] = useState(null); //the description of the current popover
  const [editId, setEditId] = useState(null); //the id of category we want to edit
  const [selectedMovie, setSelectedMovie] = useState(null); //the movie that is clicked on
  const [stars, setStars] = useState(null); //the stars for the updated rating
  const [reviewDescription, setReviewDescription] = useState(null); //the description associated with the review
  const [movieModalVisible, setMovieModalVisible] = useState(false); //controls the modal that displays the review details for movie
  const [selectedCategory, setSelectedCategory] = useState(null); //the category of movie that was selected
  const [rerender, setRerender] = useState(0); //used to rerender after updated review
  const [selectedCategories, setSelectedCategories] = useState(null); //list of categories selected
  
  /**
   * Find the categories, their movies, and ratings for the users
   */
  useEffect(() => {
    axios
      .get("http://localhost:8080/category/getUserCategories", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        console.log(response.data);
        setCategoriesMoviesReviews(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [rerender]);

  /**
   * Whenever selected movie changes, the star and reviewDescription states are updated to match the selected movie
   */
  useEffect(() => {
    if (selectedMovie) {
      setStars(selectedMovie.rating.rating);
      setReviewDescription(selectedMovie.rating.description);
    }
  }, [selectedMovie]);

  /**
   * Handles when user tries creating new list
   */
  const createList = () => {
    axios
      .post(
        "http://localhost:8080/category/create",
        {
          title: listName,
          description: description,
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      )
      .then((response) => {
        setStatusModalOpen(true);
        setIsSuccessful(true);
        setListModal(false);
        setCategoriesMoviesReviews((prev) => [
          ...prev,
          {
            id: response.data.id,
            title: response.data.name,
            description: response.data.description,
            movies: [],
          },
        ]);
      })
      .catch((error) => {
        setStatusModalOpen(true);
        setIsSuccessful(false);
        console.log(error);
      });
  };

  /**
   * Handles attempts to update list details such as title and description
   */
  const editList = () => {
    axios
      .put(
        `http://localhost:8080/category/update/${editId}`,
        {
          title: listName,
          description: description,
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      )
      .then((response) => {
        console.log(response)
        setListName("");
        setDescription("");
        setListModal(false);
        setRerender(prev=>prev+1)
      })
      .catch((error) => {
        console.log(error);
      });
  };

  /**
   * Handles the deletion of the list
   * When list is deleted, the association between that list and movie is severed
   */
  const deleteList = (deleteListId) => {
    axios
      .delete(`http://localhost:8080/category/delete/${deleteListId}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        setCategoriesMoviesReviews((prev) =>
          prev.filter((item) => item.id !== deleteListId)
        );
      })
      .then((error) => console.log(error));
  };

  /**
   * Finds the categories that the movie originally belonged to
   * @param {*} movie the title of the movie 
   */
  const getMoviesSelectedCategories = (movie)=>{
    axios.get(`http://localhost:8080/movie/getCategories/${movie}`,{
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
    })
    .then(response=>{
        setSelectedCategories(response.data)
    })
    .catch(error=>{
        console.log(error)
    })
  }

  /**
   * Updates the movie to contain the updated categories the movie belongs to
   * @param {*} movie title of the movie
   */
  const updateMoviesSelectedCategories = (movie)=>{
    console.log(selectedCategories)
    axios.put(`http://localhost:8080/movie/updateCategories/${movie}`,selectedCategories,{
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
    })
    .then(response=>{
        console.log(response.data)
    })
    .catch(error=>{
        console.log(error)
    })
  }

  /**
   * Updates the stars and description of the rating for the movie
   * @param {*} movie title of the movie
   */
  const updateMovie = (movie) => {
    axios
      .put(
        `http://localhost:8080/rating/update/${movie}`,
        {
          rating: parseInt(stars),
          description: reviewDescription,
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      )
      .then((response) => {
        setRerender((prev) => prev + 1);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  /**
   * Removes the movie from selected category
   */
  const deleteMovieFromCategory = () => {
    axios
      .delete(
        `http://localhost:8080/movie/delete/${selectedMovie.id}/${selectedCategory}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      )
      .then((response) => {
        setRerender((prev) => prev + 1);
        setMovieModalVisible(false);
      })
      .catch((error) => console.log(error));
  };

  /**
   * When a user deletes a review for a movie, find all of the users lists and delete the lists from that movie
   */
  const deleteReview = () => {
    axios
      .delete(`http://localhost:8080/rating/delete/${selectedMovie.id}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        console.log(response);
        setRerender((prev) => prev + 1);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <Container
      maxWidth={false}
      sx={{
        width: "100vw",
        height: "100vh",
        display: "flex",
        flexDirection: "column",
        justifyContent: "flex-start",
        overflowY: "scroll",
      }}
    >
      <NavigationBar></NavigationBar>
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          marginTop: "100px",
        }}
      >
        <Button
          variant="contained"
          sx={{ backgroundColor: "#db0000" }}
          onClick={() => {
            setListModal(true);
            setIsCreate(true);
          }}
        >
          Create New List
        </Button>
      </Box>
      <Modal
        open={listModal}
        onClose={() => setListModal(false)}
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          width: "100vw",
          height: "100vh",
        }}
      >
        <Box
          sx={{
            height: "400px",
            width: "350px",
            backgroundColor: "#FFFFFF",
            borderRadius: 3,
            display: "flex",
            flexDirection: "column",
            justifyContent: "space-evenly",
            alignItems: "center",
          }}
        >
          <TextField
            label="List Title(max 255 characters)"
            value={listName}
            onChange={(e) => setListName(e.target.value)}
            sx={{ width: "100%" }}
            required
          />
          <TextField
            label="Add list description here(max 255 characters)"
            multiline
            rows={6}
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            sx={{ width: "100%" }}
            required
          />
          <Button
            variant="contained"
            sx={{ backgroundColor: "#db0000" }}
            onClick={() => {
              if (isCreate) {
                createList();
              } else {
                editList();
              }
            }}
          >
            {isCreate ? "Create" : "Save"}
          </Button>
        </Box>
      </Modal>
      <Modal
        open={statusModalOpen}
        onClose={() => setStatusModalOpen(false)}
        sx={{
          width: "100vw",
          height: "100vh",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <Alert severity={isSuccessful ? "success" : "error"}>
          {isSuccessful
            ? "New list successfully created"
            : "Error creating new list"}
        </Alert>
      </Modal>
      {categoriesMoviesReviews &&
        Array.isArray(categoriesMoviesReviews) &&
        categoriesMoviesReviews.map((item) => (
          <Box
            key={item.id}
            sx={{ heigth: "100%", width: "100vw", marginTop: "20px" }}
          >
            <Box
              sx={{
                display: "flex",
                flexDirection: "row",
                alignItems: "center",
                justifyContent: "space-between",
              }}
            >
              <Box
                sx={{
                  width: "auto",
                  display: "flex",
                  flexDirection: "row",
                  justifyContent: "space-evenly",
                  alignItems: "center",
                }}
              >
                <Typography
                  sx={{
                    fontFamily: "sans-serif",
                    fontSize: 24,
                    fontWeight: 600,
                  }}
                >
                  {item.title}
                </Typography>
                <IconButton
                  onClick={(e) => {
                    setListDescriptionEl(e.currentTarget);
                    setDisplayedDescription(item.description);
                  }}
                >
                  <InfoIcon color="primary"></InfoIcon>
                </IconButton>
              </Box>
              <Box
                sx={{
                  display: "flex",
                  flexDirection: "row",
                  justifyContent: "space-evenly",
                  alignItems: "center",
                  width: "200px",
                }}
              >
                <Button
                  variant="outlined"
                  sx={{ color: "#db0000", borderColor: "#db0000" }}
                  onClick={() => {
                    setListModal(true);
                    setIsCreate(false);
                    setEditId(item.id);
                    setListName(categoriesMoviesReviews.find(category=>category.id===item.id).title);
                    setDescription(categoriesMoviesReviews.find(category=>category.id===item.id).description)
                  }}
                >
                  Edit
                </Button>
                <Button
                  variant="contained"
                  sx={{ backgroundColor: "#db0000" }}
                  onClick={() => {
                    deleteList(item.id);
                  }}
                >
                  Delete
                </Button>
              </Box>
              <Popover
                open={listDescriptionEl ? true : false}
                anchorEl={listDescriptionEl}
                onClose={() => setListDescriptionEl(null)}
                anchorOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
                sx={{
                    width:'700px'
                }}
              >
                {displayedDescription}
              </Popover>
            </Box>
            <Box
              sx={{
                width: "100vw",
                height: "225px",
                marginTop: "20px",
                display: "flex",
                flexDirection: "row",
                overflowX: "scroll",
              }}
            >
              {item.movies &&
                Array.isArray(item.movies) &&
                item.movies.map((movie) => (
                  <Box
                    key={movie.id}
                    sx={{
                      flexShrink: 0,
                      height: "95%",
                      marginRight: "20px",
                      borderRadius: 3,
                      overflow: "hidden",
                      display: "flex",
                      flexDirection: "column",
                    }}
                    onClick={() => {
                      setSelectedMovie(movie);
                      setSelectedCategory(item.id);
                      setMovieModalVisible(true);
                      getMoviesSelectedCategories(movie.title)
                    }}
                  >
                    <img src={movie.img} height="90%"></img>
                    <Rating value={movie.rating.rating ?? 0} readOnly></Rating>
                  </Box>
                ))}
            </Box>
          </Box>
        ))}
      {selectedMovie && (
        <Modal
          open={movieModalVisible}
          onClose={() => {
            setMovieModalVisible(false);
            setStars(selectedMovie.rating.rating);
            setReviewDescription(selectedMovie.rating.description);
          }}
          sx={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <Box
            sx={{
              height: "400px",
              width: "350px",
              backgroundColor: "#FFFFFF",
              borderRadius: 3,
              display: "flex",
              flexDirection: "column",
              justifyContent: "space-evenly",
              alignItems: "center",
            }}
          >
            <Rating
              value={stars ?? 0}
              onChange={(e) => setStars(e.target.value)}
              required
            ></Rating>
            <TextField
              label="Add review here(max 255 characters)"
              value={reviewDescription ?? ""}
              onChange={(e) => setReviewDescription(e.target.value)}
              multiline
              rows={6}
              sx={{ width: "100%" }}
              required
            />
            <FormControl required sx={{ width: "200px" }}>
              <InputLabel id="category-label" sx={{ color: "#000000" }}>
                Choose List(s)
              </InputLabel>
              <Select
                labelId="category-label"
                multiple
                value={selectedCategories ?? []}
                onChange={(e) => {setSelectedCategories(e.target.value)}}
                label="Choose List(s)"
                MenuProps={{
                  PaperProps: {
                    style: {
                      height: "100px",
                      overflowY: "scroll",
                    },
                  },
                }}
                sx={{
                  "& .MuiOutlinedInput-root fieldset": {
                    borderColor: "#000000",
                  },
                  border: 1,
                  borderColor: "#FFFFFF",
                  borderRadius: 2,
                  color: "#000000",
                }}
              >
                {categoriesMoviesReviews && Array.isArray(categoriesMoviesReviews) &&
                  categoriesMoviesReviews.map((category) => 
                    <MenuItem key={category.id} value={category.id}>
                      {category.title}
                    </MenuItem>
                  )
                }
              </Select>
            </FormControl>
            <Button
              variant="outlined"
              sx={{ color: "#db0000", borderColor: "#db0000" }}
              onClick={() => {
                if (selectedMovie.rating.rating !== null) {
                  updateMovie(selectedMovie.title);
                  updateMoviesSelectedCategories(selectedMovie.title)
                } 
              }}
            >
              Save Changes
            </Button>
            <Button
              variant="contained"
              sx={{ backgroundColor: "#db0000" }}
              onClick={() => {
                deleteMovieFromCategory();
              }}
            >
              Delete from Category
            </Button>
            <Button
              variant="contained"
              sx={{ backgroundColor: "#db0000" }}
              onClick={() => {
                deleteReview();
              }}
            >
              Delete Review
            </Button>
          </Box>
        </Modal>
      )}
    </Container>
  );
};

export default Lists;
