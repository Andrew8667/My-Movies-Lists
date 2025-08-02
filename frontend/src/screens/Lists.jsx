import {
  Container,
  Typography,
  AppBar,
  Toolbar,
  IconButton,
  Avatar,
  Box,
  TextField,
  Button,
  Paper,
  Drawer,
  ListItem,
  ListItemText,
  ListItemButton,
  Modal,
  Popover,
  Alert,
  Rating,
  FormControl,
  InputLabel,
  Select,
} from "@mui/material";
import InfoIcon from "@mui/icons-material/Info";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useNavigation } from "react-router-dom";
import MenuIcon from "@mui/icons-material/Menu";
import NavigationBar from "../components/NavigationBar";

const Lists = function Lists() {
  const navigate = useNavigate();
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
  useEffect(() => {
    //find the categories, their movies, and ratings for the users
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
  }, []);

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
   * Handles when user tries creating new list
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
        console.log(response.data);
        setCategoriesMoviesReviews((prev) =>
          prev.map((category) =>
            category.id === editId
              ? {
                  ...category,
                  title: listName,
                  description: description,
                }
              : category
          )
        );
        setListName("");
        setDescription("");
        setListModal(false);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  /**
   * Handles the deletion of the list and the removal of the list from the movies
   */
  const deleteList = (deleteListId) => {
    axios
      .delete(`http://localhost:8080/category/delete/${deleteListId}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        console.log(response);
        setCategoriesMoviesReviews((prev) =>
          prev.filter((item) => item.id !== deleteListId)
        );
      })
      .then((error) => console.log(error));
  };

  const updateMovie = (categoryId) => {
    axios
      .put(
        `http://localhost:8080/rating/update/${selectedMovie.id}`,
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
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const addReview = async () => {
    try {
      await axios.post(
        "http://localhost:8080/movie/add",
        {
          title: selectedMovie.title,
          img: selectedMovie.img,
          stars: stars,
          review: reviewDescription,
          categories: [],
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );
      console.log("success");
    } catch (error) {
      console.error(error);
    }
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
            label="List Title"
            value={listName}
            onChange={(e) => setListName(e.target.value)}
            sx={{ width: "100%" }}
            required
          />
          <TextField
            label="Add list description here"
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
              label="Add review here"
              value={reviewDescription ?? ""}
              onChange={(e) => setReviewDescription(e.target.value)}
              multiline
              rows={6}
              sx={{ width: "100%" }}
              required
            />
            <Button
              variant="contained"
              sx={{ backgroundColor: "#db0000" }}
              onClick={() => {
                if (selectedMovie.rating.rating !== null) {
                  updateMovie(selectedCategory);
                } else {
                  addReview();
                }
              }}
            >
              Save Changes
            </Button>
          </Box>
        </Modal>
      )}
    </Container>
  );
};

export default Lists;
